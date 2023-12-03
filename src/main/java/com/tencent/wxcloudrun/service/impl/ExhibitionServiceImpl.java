package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefExhibition;
import com.tencent.wxcloudrun.dto.request.AddExhibitionRequest;
import com.tencent.wxcloudrun.dto.request.UpdateExhibitionRequest;
import com.tencent.wxcloudrun.entity.Exhibition;
import com.tencent.wxcloudrun.mapper.ExhibitScriptMapper;
import com.tencent.wxcloudrun.mapper.ExhibitionMapper;
import com.tencent.wxcloudrun.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    @Autowired
    private ExhibitionMapper exhibitionMapper;

    @Autowired
    private ExhibitScriptMapper exhibitScriptMapper;

    @Override
    public Long addExhibition(AddExhibitionRequest request) {

        Exhibition exhibition = Exhibition.builder()
                .name(request.getName())
                .place(request.getPlace())
                .lotteryTime(request.getLotteryTime())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .exhibitionAbbreviation(request.getExhibitionAbbreviation())
                .build();

        if (request.getPoster() != null) exhibition.setPoster(request.getPoster());

        exhibitionMapper.insert(exhibition);

        return exhibition.getId();

    }

    @Override
    public Page<BriefExhibition> getExhibitionListByStatus(int pageSize, int pageNum, int status) {

        PageHelper.startPage(pageNum, pageSize);
        List<BriefExhibition> briefExhibitions = exhibitionMapper.getExhibitionListByStatus(status);
        PageInfo<BriefExhibition> pageInfo = new PageInfo<>(briefExhibitions);

        for (BriefExhibition briefExhibition : briefExhibitions) {
            briefExhibition.setScriptNum(exhibitScriptMapper.getScriptNumByExhibitionId(briefExhibition.getId()));
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefExhibitions);

    }

    @Override
    public void updateExhibition(UpdateExhibitionRequest request) {

        Exhibition exhibition = exhibitionMapper.selectById(request.getId());
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);

        if (request.getName() != null) exhibition.setName(request.getName());
        if (request.getPlace() != null) exhibition.setPlace(request.getPlace());
        if (request.getLotteryTime() != null) exhibition.setLotteryTime(request.getLotteryTime());
        if (request.getStartTime() != null) exhibition.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) exhibition.setEndTime(request.getEndTime());
        if (request.getExhibitionAbbreviation() != null) exhibition.setExhibitionAbbreviation(request.getExhibitionAbbreviation());
        if (request.getStatus() != null) exhibition.setStatus(request.getStatus());
        if (request.getPoster() != null) exhibition.setPoster(request.getPoster());

        exhibitionMapper.updateById(exhibition);
    }

    @Override
    public Exhibition getExhibitionById(Long id) {
        Exhibition exhibition = exhibitionMapper.selectById(id);
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);
        return exhibition;
    }

}
