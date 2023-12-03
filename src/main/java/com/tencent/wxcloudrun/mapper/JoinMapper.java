package com.tencent.wxcloudrun.mapper;

import com.tencent.wxcloudrun.dto.BriefScriptExhibitSchedule;
import com.tencent.wxcloudrun.dto.ScriptSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JoinMapper {

    @Select("select * from exhibition join script_exhibit_schedule on exhibition.id=script_exhibit_schedule.exhibition_id where script_exhibit_schedule.script_id=#{scriptId}")
    List<BriefScriptExhibitSchedule> getBriefScriptExhibitScheduleListByScriptId(
            @Param("scriptId") Long scriptId);

    @Select("select exhibition.id as exhibition_id, exhibition.name, exhibition.start_time, exhibition.end_time, exhibit_script.script_id from exhibition join exhibit_script on exhibition.id=exhibit_script.exhibition_id where exhibit_script.script_id=#{scriptId}")
    List<ScriptSchedule> getScriptScheduleListByScriptId(
            @Param("scriptId") Long scriptId);

}
