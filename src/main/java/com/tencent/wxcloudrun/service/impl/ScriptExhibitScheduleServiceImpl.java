package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefScriptExhibitSchedule;
import com.tencent.wxcloudrun.dto.request.GetBriefScriptExhibitScheduleListRequest;
import com.tencent.wxcloudrun.mapper.JoinMapper;
import com.tencent.wxcloudrun.mapper.ScriptExhibitScheduleMapper;
import com.tencent.wxcloudrun.service.ScriptExhibitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptExhibitScheduleServiceImpl implements ScriptExhibitScheduleService {

    @Autowired
    private ScriptExhibitScheduleMapper scriptExhibitScheduleMapper;

    @Autowired
    private JoinMapper joinMapper;

    @Override
    public Page<BriefScriptExhibitSchedule> getBriefScriptExhibitScheduleList(GetBriefScriptExhibitScheduleListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<BriefScriptExhibitSchedule> briefScriptExhibitScheduleList = joinMapper.getBriefScriptExhibitScheduleListByScriptId(request.getScriptId());
        return new Page<>(new PageInfo<>(briefScriptExhibitScheduleList));
    }


}
