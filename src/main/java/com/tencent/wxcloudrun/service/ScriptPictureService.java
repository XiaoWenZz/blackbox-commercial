package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.entity.ScriptPicture;
import org.springframework.web.multipart.MultipartFile;


public interface ScriptPictureService {

    Page<ScriptPicture> getScriptPictureByScriptId(Long scriptId, Integer page, Integer size);

    String uploadScriptPicture(Long scriptId, MultipartFile file);

}
