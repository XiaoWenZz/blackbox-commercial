package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefScriptExhibitSchedule;
import com.tencent.wxcloudrun.dto.request.GetBriefScriptExhibitScheduleListRequest;

public interface ScriptExhibitScheduleService {

    Page<BriefScriptExhibitSchedule> getBriefScriptExhibitScheduleList(GetBriefScriptExhibitScheduleListRequest request);
}
