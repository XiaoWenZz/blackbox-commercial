package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefExhibitScript;
import com.tencent.wxcloudrun.dto.BriefMerchantExhibitScript;
import com.tencent.wxcloudrun.dto.ExhibitScriptFlagForUser;
import com.tencent.wxcloudrun.dto.request.AddExhibitScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetExhibitScriptListRequest;
import com.tencent.wxcloudrun.dto.request.SendScriptMessageRequest;
import com.tencent.wxcloudrun.entity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.service.ExhibitScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExhibitScriptServiceImpl implements ExhibitScriptService {

    @Autowired
    private ExhibitionMapper exhibitionMapper;

    @Autowired
    private ExhibitScriptMapper exhibitScriptMapper;

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private ScriptCollectionMapper scriptCollectionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExhibitScriptFlagMapper exhibitScriptFlagMapper;

    @Override
    public Page<BriefExhibitScript> getBriefExhibitScriptList(GetExhibitScriptListRequest request) {

        Exhibition exhibition = exhibitionMapper.selectById(request.getExhibitionId());
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);

        List<Long> scriptIdList;

        if (request.getDebutFlag() == null){
            scriptIdList = exhibitScriptMapper.getScriptIdListByExhibitId(request.getExhibitionId());
        }

        else scriptIdList = exhibitScriptMapper.getScriptIdListByExhibitIdAndDebutFlag(request.getExhibitionId(), request.getDebutFlag());

        QueryWrapper<Script> scriptQueryWrapper = new QueryWrapper<>();
        scriptQueryWrapper.in("id", scriptIdList);
        if (request.getType() != null) scriptQueryWrapper.eq("type", request.getType());
        if (request.getSaleForm() != null) scriptQueryWrapper.eq("sale_form", request.getSaleForm());
        if (request.getFaction() != null) scriptQueryWrapper.eq("faction", request.getFaction());
        if (request.getDifficulty() != null) scriptQueryWrapper.eq("difficulty", request.getDifficulty());
        if (request.getTheme() != null) scriptQueryWrapper.eq("theme", request.getTheme());
        if (request.getRank() != null) {
            if (request.getRank() == 1) scriptQueryWrapper.orderByDesc("collection_count");
            else if (request.getRank() == 0) scriptQueryWrapper.orderByAsc("collection_count");
        }

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Script> scriptList = scriptMapper.selectList(scriptQueryWrapper);
        if (scriptList == null || scriptList.size() == 0) return null;
        PageInfo<Script> pageInfo = new PageInfo<>(scriptList);
        List<BriefExhibitScript> briefExhibitScripts = new ArrayList<>();

        for (Script script : scriptList) {

            ExhibitScript exhibitScript = exhibitScriptMapper.getExhibitScriptByScriptIdAndExhibitionId(script.getId(), request.getExhibitionId());
            if (exhibitScript == null) throw new CommonException(CommonErrorCode.EXHIBIT_SCRIPT_NOT_EXIST);

            briefExhibitScripts.add(BriefExhibitScript.builder()
                    .id(script.getId())
                    .name(script.getName())
                    .type(script.getType())
                    .saleForm(script.getSaleForm())
                    .room(exhibitScript.getRoom())
                    .fileid(script.getFileId())
                    .collectionCount(script.getCollectionCount())
                    .debutFlag(exhibitScriptMapper.getDebutFlagByExhibitIdAndScriptId(request.getExhibitionId(), script.getId()))
                    .author1Name(script.getAuthor1Name())
                    .author2Name(script.getAuthor2Name())
                    .author3Name(script.getAuthor3Name())
                    .merchant1Name(script.getMerchant1Name())
                    .merchant2Name(script.getMerchant2Name())
                    .merchant3Name(script.getMerchant3Name())
                    .composition(script.getComposition())
                    .tag(script.getTag())
                    .theme(script.getTheme())
                    .faction(script.getFaction())
                    .difficulty(script.getDifficulty())
                    .build());
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefExhibitScripts);
    }

    @Override
    public void addExhibitScript(AddExhibitScriptRequest request) {

        Exhibition exhibition = exhibitionMapper.selectById(request.getExhibitionId());
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);

        Script script = scriptMapper.selectById(request.getScriptId());
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        ExhibitScript exhibitScript = ExhibitScript.builder()
                .scriptId(request.getScriptId())
                .exhibitionId(request.getExhibitionId())
                .debutFlag(request.getDebutFlag())
                .room(request.getRoom())
                .discussCount(0)
                .build();

        exhibitScriptMapper.insert(exhibitScript);

    }

    @Override
    public Page<BriefMerchantExhibitScript> getExhibitScriptList(Integer pageNum, Integer pageSize, Long exhibitionId, Long merchantId) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(exhibitScriptMapper.getExhibitScriptListByExhibitionIdAndMerchantId(exhibitionId, merchantId)));
    }

    @Override
    public List<String> sendScriptMessage(SendScriptMessageRequest request) {

        ExhibitScript exhibitScript = exhibitScriptMapper.getExhibitScriptByScriptIdAndExhibitionId(request.getScriptId(), request.getExhibitionId());
        if (exhibitScript == null) throw new CommonException(CommonErrorCode.EXHIBIT_SCRIPT_NOT_EXIST);

        if (exhibitScript.getNotified() == 1) throw new CommonException(CommonErrorCode.SCRIPT_HAS_BEEN_NOTIFIED);

        List<Long> userIds = scriptCollectionMapper.getCollectionUserIdList(exhibitScript.getScriptId());
        if (userIds.isEmpty()) throw new CommonException(CommonErrorCode.NO_USER_COLLECT_SCRIPT);

        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("template_id", CommonConstants.TEMPLATE_ID);
        requestBody.put("page", request.getPage());
        requestBody.put("lang", request.getLang());
        requestBody.put("miniprogram_state", request.getMiniprogramState());

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> characterString1 = new HashMap<>();

        characterString1.put("value", request.getValue1());
        data.put("character_string1", characterString1);

        Map<String, Object> thing2 = new HashMap<>();
        thing2.put("value", request.getValue2());
        data.put("thing2", thing2);

        Map<String, Object> thing3 = new HashMap<>();
        thing3.put("value", request.getValue3());
        data.put("thing3", thing3);

        requestBody.put("data", data);

        List<String> responseList = new ArrayList<>();

        for (Long userId : userIds) {
            User user = userMapper.selectById(userId);
            if (user == null) continue;
            if (user.getOpenId() == null) continue;
            requestBody.put("touser", user.getOpenId());

            // 发送POST请求
            ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestBody, byte[].class);
            if (responseEntity.getStatusCode() != HttpStatus.OK) throw new CommonException(CommonErrorCode.SEND_MESSAGE_ERROR);
            responseList.add(new String(responseEntity.getBody(), StandardCharsets.UTF_8));
        }

        exhibitScript.setNotified(1);
        exhibitScriptMapper.updateById(exhibitScript);
        return responseList;
    }

    @Override
    public Long flagExhibitScript(Long id, Integer attitude, String unionId) {

        ExhibitScript exhibitScript = exhibitScriptMapper.selectById(id);
        if (exhibitScript == null) throw new CommonException(CommonErrorCode.EXHIBIT_SCRIPT_NOT_EXIST);

        User user = userMapper.getUserByUnionId(unionId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        ExhibitScriptFlag exhibitScriptFlag0 = exhibitScriptFlagMapper.selectByUserIdAndExhibitScriptId(user.getId(), id);

        if (exhibitScriptFlag0 != null) {
            if (exhibitScriptFlag0.getAttitude().equals(attitude)) {
                exhibitScriptFlagMapper.deleteById(exhibitScriptFlag0.getId());
                return null;
            } else {
                exhibitScriptFlag0.setAttitude(attitude);
                exhibitScriptFlagMapper.updateById(exhibitScriptFlag0);
                return exhibitScriptFlag0.getId();
            }
        }

        ExhibitScriptFlag exhibitScriptFlag = ExhibitScriptFlag.builder()
                .exhibitScriptId(id)
                .attitude(attitude)
                .userId(user.getId())
                .build();

        exhibitScriptMapper.insert(exhibitScript);
        return exhibitScriptFlag.getId();
    }

    @Override
    public ExhibitScriptFlagForUser getExhibitScriptFlagForUser(Long id, String unionId) {

        User user = userMapper.getUserByUnionId(unionId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        QueryWrapper<ExhibitScriptFlag> exhibitScriptFlagQueryWrapper = new QueryWrapper<>();
        exhibitScriptFlagQueryWrapper.eq("exhibit_script_id", id);

        Integer supportFlagCount = exhibitScriptFlagMapper.selectCount(exhibitScriptFlagQueryWrapper.eq("attitude", 1));
        Integer opposeFlagCount = exhibitScriptFlagMapper.selectCount(exhibitScriptFlagQueryWrapper.eq("attitude", 0));
        Integer attitude = exhibitScriptFlagMapper.selectOne(exhibitScriptFlagQueryWrapper.eq("user_id", userMapper.getUserByUnionId(unionId).getId())).getAttitude();

        return ExhibitScriptFlagForUser.builder()
                .supportCount(supportFlagCount)
                .waitCount(opposeFlagCount)
                .attitude(attitude)
                .build();
    }

}
