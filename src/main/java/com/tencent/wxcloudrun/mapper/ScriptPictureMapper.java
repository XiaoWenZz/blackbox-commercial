package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.ScriptPicture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptPictureMapper extends BaseMapper<ScriptPicture> {

    @Select("SELECT * FROM script_picture WHERE script_id = #{scriptId}")
    List<ScriptPicture> getScriptPictureByScriptId(
            @Param("scriptId") Long scriptId);

    @Select("SELECT * FROM script_picture WHERE name = #{name}")
    ScriptPicture getScriptPictureByName(
            @Param("name") String name);


}
