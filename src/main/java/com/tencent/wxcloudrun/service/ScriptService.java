package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.ScriptSchedule;
import com.tencent.wxcloudrun.dto.request.AddScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetScriptScheduleListRequest;
import com.tencent.wxcloudrun.dto.request.ScriptFilterRequest;
import com.tencent.wxcloudrun.dto.request.UpdateScriptRequest;
import com.tencent.wxcloudrun.entity.Script;
import org.springframework.web.multipart.MultipartFile;

public interface ScriptService {
    String uploadScriptPicture(MultipartFile file, Long scriptId);

    Long addScript(AddScriptRequest addScriptRequest);

    Script getScriptById(Long scriptId);

    Page<Script> getBriefScriptListWithFilter(ScriptFilterRequest request);

    String uploadPicture(MultipartFile file, Long scriptId);

    void updateScript(UpdateScriptRequest request);

    Page<ScriptSchedule> getScriptScheduleList(GetScriptScheduleListRequest request);

}
