package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.ExhibitScriptFlag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExhibitScriptFlagMapper extends BaseMapper<ExhibitScriptFlag> {

    @Select("SELECT * FROM exhibit_script_flag WHERE user_id = #{userId} AND exhibit_script_id = #{exhibitScriptId}")
    ExhibitScriptFlag selectByUserIdAndExhibitScriptId(
            @Param("userId") Long userId,
            @Param("exhibitScriptId") Long exhibitScriptId);
}
