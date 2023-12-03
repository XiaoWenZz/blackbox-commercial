package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddPreviewRequest;
import com.tencent.wxcloudrun.entity.Merchant;
import com.tencent.wxcloudrun.entity.Preview;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.MerchantMapper;
import com.tencent.wxcloudrun.mapper.PreviewMapper;
import com.tencent.wxcloudrun.mapper.ScriptCollectionMapper;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.service.PreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PreviewServiceImpl implements PreviewService {

    @Autowired
    private PreviewMapper previewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private ScriptCollectionMapper scriptCollectionMapper;

    @Autowired
    private RestTemplate restTemplate;


    private final Map<Long, Runnable> startPreviews = new HashMap<>();
    private final Map<Long, Runnable> endPreviews = new HashMap<>();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addPreview(String openId, AddPreviewRequest request) {

        User user = userMapper.getUserByUnionId(openId);
        if (user.getUserType() != 1 && user.getUserType() != 2) throw new CommonException(CommonErrorCode.USER_NOT_PUBLISHER);

        Merchant merchant = merchantMapper.selectById(user.getPublisherId());
        if (merchant == null) throw new CommonException(CommonErrorCode.USER_NOT_PUBLISHER);

        if (merchant.getPublishCount() < 1) throw new CommonException(CommonErrorCode.PUBLISH_COUNT_LIMIT);

        int count = previewMapper.getPreviewCount(request.getTimeSlot(), request.getExhibitionDateId(), request.getExhibitionId());
        if (count >= 5) throw new CommonException(CommonErrorCode.PREVIEW_COUNT_LIMIT);

        Preview preview = Preview.builder()
                .exhibitionDateId(request.getExhibitionDateId())
                .exhibitionId(request.getExhibitionId())
                .exhibitionRoom(request.getExhibitionRoom())
                .content(request.getContent())
                .endTime(request.getEndTime())
                .startTime(request.getStartTime())
                .scriptId(request.getScriptId())
                .scriptName(request.getScriptName())
                .scriptCover(request.getScriptCover())
                .timeSlot(request.getTimeSlot())
                .merchantId(merchant.getId())
                .status(0)
                .build();

        previewMapper.insert(preview);
        scheduledStartPreview(preview);
        scheduledEndPreview(preview);

        merchant.setPublishCount(merchant.getPublishCount() - 1);
        merchantMapper.updateById(merchant);

        return preview.getId();
    }

    @Override
    public void scheduledStartPreview(Preview preview) {
        Runnable startPreview = () -> startPreview(preview.getId());
        Date startTime = Date.from(LocalDateTime.parse(preview.getStartTime()).atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(startPreview, startTime);
        startPreviews.put(preview.getId(), startPreview);
    }

    @Override
    public void scheduledEndPreview(Preview preview) {
        Runnable endPreview = () -> endPreview(preview.getId());
        Date endTime = Date.from(LocalDateTime.parse(preview.getEndTime()).atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(endPreview, endTime);
        endPreviews.put(preview.getId(), endPreview);
    }

    @Override
    public void startPreview(Long previewId) {
        Preview preview = previewMapper.selectById(previewId);
        if (preview == null) throw new CommonException(CommonErrorCode.PREVIEW_NOT_EXIST);

        if (preview.getStatus() == 0) {
            preview.setStatus(1);
            previewMapper.updateById(preview);
        }

        else if (preview.getStatus() == 1) throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_STARTED);
        else throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_ENDED);

        startPreviews.remove(previewId);
    }

    @Override
    public void endPreview(Long previewId) {
        Preview preview = previewMapper.selectById(previewId);
        if (preview == null) throw new CommonException(CommonErrorCode.PREVIEW_NOT_EXIST);

        if (preview.getStatus() == 0) throw new CommonException(CommonErrorCode.PREVIEW_NOT_STARTED);

        else if (preview.getStatus() == 1) {
            preview.setStatus(2);
            previewMapper.updateById(preview);
        }

        else throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_ENDED);

        endPreviews.remove(previewId);
    }

    @Override
    public void cancelScheduledStartPreview(Long previewId) {

        Runnable startPreview = startPreviews.get(previewId);
        Runnable endPreview = endPreviews.get(previewId);

        if (startPreview != null) taskScheduler.getScheduledThreadPoolExecutor().remove(startPreview);
        if (endPreview != null) taskScheduler.getScheduledThreadPoolExecutor().remove(endPreview);

        Preview preview = previewMapper.selectById(previewId);
        if (preview.getStatus() == 1) throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_STARTED);
        else if (preview.getStatus() == 2) throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_ENDED);
        else if (preview.getStatus() == 3) throw new CommonException(CommonErrorCode.PREVIEW_ALREADY_CANCELED);

        preview.setStatus(3);
        previewMapper.updateById(preview);

        Merchant merchant = merchantMapper.selectById(preview.getMerchantId());
        merchant.setPublishCount(merchant.getPublishCount() + 1);
        merchantMapper.updateById(merchant);

        startPreviews.remove(previewId);
        endPreviews.remove(previewId);


    }

    @Override
    public Page<Preview> getExhibitionPreviewList(Integer pageNum, Integer pageSize, Long exhibitionId) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(previewMapper.getPreviewsByExhibitionId(exhibitionId)));
    }

    @Override
    public List<Integer> getPreviewCount(Long exhibitionId, Integer exhibitionDateId) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            list.add(previewMapper.getPreviewCount(i, exhibitionDateId, exhibitionId));
        }
        return list;
    }

    @Override
    public Page<Preview> getRunningPreviewList(Integer pageNum, Integer pageSize, Long exhibitionId) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(previewMapper.getRunningPreviews(exhibitionId)));
    }
}
