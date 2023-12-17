package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.ScriptSchedule;
import com.tencent.wxcloudrun.dto.request.AddScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetScriptScheduleListRequest;
import com.tencent.wxcloudrun.dto.request.ScriptFilterRequest;
import com.tencent.wxcloudrun.dto.request.UpdateScriptRequest;
import com.tencent.wxcloudrun.entity.Merchant;
import com.tencent.wxcloudrun.entity.Script;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.service.ScriptService;
import com.tencent.wxcloudrun.util.SessionUtils;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScriptCollectionMapper scriptCollectionMapper;

    @Autowired
    private JoinMapper joinMapper;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public String uploadScriptPicture(MultipartFile file, Long scriptId) {
        return null;
    }

    @Override
    public Long addScript(AddScriptRequest addScriptRequest) {

        Merchant merchant = merchantMapper.selectById(addScriptRequest.getMerchant1Id());
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        Script script = Script.builder()
                .name(addScriptRequest.getName())
                .author1Name(addScriptRequest.getAuthor1Name())
                .merchant1Id(addScriptRequest.getMerchant1Id())
                .merchant1Name(merchant.getName())
                .introduction(addScriptRequest.getIntroduction())
                .type(addScriptRequest.getType())
                .saleForm(addScriptRequest.getSaleForm())
                .tag(addScriptRequest.getTag())
                .faction(addScriptRequest.getFaction())
                .theme(addScriptRequest.getTheme())
                .difficulty(addScriptRequest.getDifficulty())
                .heat(0)
                .build();

        if (addScriptRequest.getMerchant2Id() != null) {
            Merchant merchant2 = merchantMapper.selectById(addScriptRequest.getMerchant2Id());
            if (merchant2 == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);
            script.setMerchant2Id(merchant2.getId());
            script.setMerchant2Name(merchant2.getName());
        }

        if (addScriptRequest.getMerchant3Id() != null) {
            Merchant merchant3 = merchantMapper.selectById(addScriptRequest.getMerchant3Id());
            if (merchant3 == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);
            script.setMerchant3Id(merchant3.getId());
            script.setMerchant3Name(merchant3.getName());
        }



        if (addScriptRequest.getAuthor2Name() != null) script.setAuthor2Name(addScriptRequest.getAuthor2Name());
        if (addScriptRequest.getAuthor3Name() != null) script.setAuthor3Name(addScriptRequest.getAuthor3Name());
        if (addScriptRequest.getPlayerCount() != null) script.setPlayerCount(addScriptRequest.getPlayerCount());
        if (addScriptRequest.getMaleCount() != null) script.setMaleCount(addScriptRequest.getMaleCount());
        if (addScriptRequest.getFemaleCount() != null) script.setFemaleCount(addScriptRequest.getFemaleCount());
        if (addScriptRequest.getNeeds() != null) script.setNeeds(addScriptRequest.getNeeds());
        if (addScriptRequest.getAfterSaleSupport() != null) script.setAfterSaleSupport(addScriptRequest.getAfterSaleSupport());
        if (addScriptRequest.getPrice() != null) script.setPrice(addScriptRequest.getPrice());
        if (addScriptRequest.getMinPlayTime() != null) script.setMinPlayTime(addScriptRequest.getMinPlayTime());
        if (addScriptRequest.getMaxPlayTime() != null) script.setMaxPlayTime(addScriptRequest.getMaxPlayTime());
        if (addScriptRequest.getIceBreaking() != null) script.setIceBreaking(addScriptRequest.getIceBreaking());
        if (addScriptRequest.getDeliveryTime() != null) script.setDeliveryTime(addScriptRequest.getDeliveryTime());
        if (addScriptRequest.getCrossDressing() != null) script.setCrossDressing(addScriptRequest.getCrossDressing());
        if (addScriptRequest.getSingleReadVolume() != null) script.setSingleReadVolume(addScriptRequest.getSingleReadVolume());
        if (addScriptRequest.getHighlight() != null) script.setHighlight(addScriptRequest.getHighlight());

        scriptMapper.insert(script);
        return script.getId();

    }

    @Override
    public Script getScriptById(Long scriptId) {
        Script script = scriptMapper.selectById(scriptId);
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        return script;
    }

    @Override
    public Page<Script> getBriefScriptListWithFilter(ScriptFilterRequest request) {

        QueryWrapper<Script> scriptQueryWrapper = new QueryWrapper<>();

        if (request.getPlayerCount() != null) {
            if (request.getPlayerCount() == -1) scriptQueryWrapper.le("player_count", 5);
            else if (request.getPlayerCount() == -2) scriptQueryWrapper.ge("player_count", 8);
            else scriptQueryWrapper.eq("player_count", request.getPlayerCount());
        }

        if (request.getType() != null) scriptQueryWrapper.eq("type", request.getType());

        if (request.getSaleForm() != null) scriptQueryWrapper.eq("sale_form", request.getSaleForm());

        if (request.getTheme() != null && !request.getTheme().isEmpty()){
            scriptQueryWrapper.in("theme", request.getTheme());
        }

        if (request.getFaction() != null && !request.getFaction().isEmpty()){
            scriptQueryWrapper.in("faction", request.getFaction());
        }

        if (request.getDifficulty() != null && !request.getDifficulty().isEmpty()){
            scriptQueryWrapper.in("difficulty", request.getDifficulty());
        }

        if (request.getName() != null) {
            scriptQueryWrapper.like("name", request.getName());
        }

        if (request.getSort() != null) {

            if (request.getSort() == 1) scriptQueryWrapper.orderByDesc("collection");
            else if (request.getSort() == 2) scriptQueryWrapper.orderByAsc("collection");

        }

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Script> scriptList = scriptMapper.selectList(scriptQueryWrapper);
        return new Page<>(new PageInfo<>(scriptList));

    }


    @Override
    public String uploadPicture(MultipartFile file, Long scriptId) {
        Script script = scriptMapper.selectById(scriptId);
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        String path = "script" + scriptId + "-" +  file.getOriginalFilename();
        String url, token = null, authorization = null, file_id = null, cos_file_id = null;
        //上传文件链接
        // 构建请求体，设置相应的参数
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        String UR_l = "https://api.weixin.qq.com/tcb/uploadfile";
        try {
            json.put("env", CommonConstants.ENV);
            json.put("path", path);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(String.valueOf(json), JSON);
        Request request = new Request.Builder()
                .url(UR_l)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            url = jsonNode.get("url").asText();
            token = jsonNode.get("token").asText();
            authorization = jsonNode.get("authorization").asText();
            file_id = jsonNode.get("file_id").asText();
            cos_file_id = jsonNode.get("cos_file_id").asText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //上传文件

        try {
            // 构建请求体，设置相应的参数
            MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");

            // 构建请求体的MultipartBody.Builder对象
            assert authorization != null;
            RequestBody fileBody = RequestBody.create(file.getBytes(), mediaType);

            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("key", path)
                    .addFormDataPart("Signature", authorization)
                    .addFormDataPart("x-cos-security-token", token)
                    .addFormDataPart("x-cos-meta-fileid", cos_file_id)
                    .addFormDataPart("file", null, fileBody);

            // 构建请求对象
            Request request1 = new Request.Builder()
                    .url("https://api.weixin.qq.com/tcb/batchdownloadfile")
                    .post(requestBodyBuilder.build())
                    .build();

            client.newCall(request1).execute();


        } catch (IOException e) {
            e.printStackTrace();
        }
        script.setFileId(file_id);
        scriptMapper.updateById(script);

        return file_id;
    }

    @Override
    public void updateScript(UpdateScriptRequest request) {

        //Admin验证有问题，暂且先这样写
        String openId = sessionUtils.getOpenId();
        User user0 = userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
        if (user0 == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);
        if (user0.getType() != 1) throw new CommonException(CommonErrorCode.USER_NOT_ADMIN);


        Script script = scriptMapper.selectById(request.getScriptId());
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        if (request.getName() != null) script.setName(request.getName());
        if (request.getAuthor1Name() != null) script.setAuthor1Name(request.getAuthor1Name());
        if (request.getAuthor2Name() != null) script.setAuthor2Name(request.getAuthor2Name());
        if (request.getAuthor3Name() != null) script.setAuthor3Name(request.getAuthor3Name());
        if (request.getMerchant1Id() != null) {
            Merchant merchant = merchantMapper.selectById(request.getMerchant1Id());
            if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);
            script.setMerchant1Id(request.getMerchant1Id());
            script.setMerchant1Name(merchant.getName());
        }
        if (request.getMerchant2Id() != null) {
            Merchant merchant = merchantMapper.selectById(request.getMerchant2Id());
            if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);
            script.setMerchant2Id(request.getMerchant2Id());
            script.setMerchant2Name(merchant.getName());
        }
        if (request.getMerchant3Id() != null) {
            Merchant merchant = merchantMapper.selectById(request.getMerchant3Id());
            if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);
            script.setMerchant3Id(request.getMerchant3Id());
            script.setMerchant3Name(merchant.getName());
        }
        if (request.getIntroduction() != null) script.setIntroduction(request.getIntroduction());
        if (request.getPlayerCount() != null) script.setPlayerCount(request.getPlayerCount());
        if (request.getMaleCount() != null) script.setMaleCount(request.getMaleCount());
        if (request.getFemaleCount() != null) script.setFemaleCount(request.getFemaleCount());
        if (request.getType() != null) script.setType(request.getType());
        if (request.getSaleForm() != null) script.setSaleForm(request.getSaleForm());
        if (request.getTag() != null) script.setTag(request.getTag());
        if (request.getFileId() != null) script.setFileId(request.getFileId());
        if (request.getDifficulty() != null) script.setDifficulty(request.getDifficulty());
        if (request.getTheme() != null) script.setTheme(request.getTheme());
        if (request.getFaction() != null) script.setFaction(request.getFaction());
        if (request.getNeeds() != null) script.setNeeds(request.getNeeds());
        if (request.getAfterSaleSupport() != null) script.setAfterSaleSupport(request.getAfterSaleSupport());
        if (request.getPrice() != null) script.setPrice(request.getPrice());
        if (request.getMinPlayTime() != null) script.setMinPlayTime(request.getMinPlayTime());
        if (request.getMaxPlayTime() != null) script.setMaxPlayTime(request.getMaxPlayTime());
        if (request.getIceBreaking() != null) script.setIceBreaking(request.getIceBreaking());
        if (request.getDeliveryTime() != null) script.setDeliveryTime(request.getDeliveryTime());
        if (request.getCrossDressing() != null) script.setCrossDressing(request.getCrossDressing());
        if (request.getSingleReadVolume() != null) script.setSingleReadVolume(request.getSingleReadVolume());
        if (request.getHighlight() != null) script.setHighlight(request.getHighlight());

        scriptMapper.updateById(script);

    }

    @Override
    public Page<ScriptSchedule> getScriptScheduleList(GetScriptScheduleListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return new Page<>(new PageInfo<>(joinMapper.getScriptScheduleListByScriptId(request.getScriptId())));
    }

}
