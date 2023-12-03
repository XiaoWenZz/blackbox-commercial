package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.entity.Script;
import com.tencent.wxcloudrun.entity.ScriptPicture;
import com.tencent.wxcloudrun.mapper.ScriptMapper;
import com.tencent.wxcloudrun.mapper.ScriptPictureMapper;
import com.tencent.wxcloudrun.service.ScriptPictureService;
import com.tencent.wxcloudrun.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ScriptPictureImpl implements ScriptPictureService {

    @Autowired
    private ScriptPictureMapper scriptPictureMapper;

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrlService urlService;


    @Override
    public Page<ScriptPicture> getScriptPictureByScriptId(Long scriptId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(scriptPictureMapper.getScriptPictureByScriptId(scriptId)));

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadScriptPicture(Long scriptId, MultipartFile file) {
        Script script = scriptMapper.selectById(scriptId);
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        String fileName = file.getOriginalFilename();
        String prefix = "blackbox-exhibition/scriptPics";


        COSClient cosClient = urlService.getCosClient();
        if (cosClient == null) throw new RuntimeException("get cos client is error!");

        try {
            String key = prefix + "/" + script.getName() + "/" + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(CommonConstants.COS_BUCKET, key, file.getInputStream(), new ObjectMetadata());
            cosClient.putObject(putObjectRequest);

        } catch (CosClientException | IOException e) {
            throw new RuntimeException("upload file to cos is error!");
        } finally {
            cosClient.shutdown();
        }

        String fileId = CommonConstants.FILE_ID_PREFIX + prefix + "/" + script.getName() + "/" + fileName;

        ScriptPicture scriptPicture = ScriptPicture.builder()
                .scriptId(scriptId)
                .fileId(fileId)
                .build();

        scriptPictureMapper.insert(scriptPicture);

        return fileId;

    }


}
