package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.ScriptCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptCollectionMapper extends BaseMapper<ScriptCollection> {

    @Select("SELECT * FROM script_collection WHERE user_id=#{userId}")
    List<ScriptCollection> getUserCollectionList(
            @Param("userId") Long userId
    );

    @Select("SELECT * FROM script_collection WHERE user_id=#{userId} AND script_id=#{scriptId}")
    ScriptCollection getCollection(
            @Param("userId") Long userId,
            @Param("scriptId") Long scriptId
    );

    @Select("SELECT user_id FROM script_collection WHERE script_id=#{scriptId}")
    List<Long> getCollectionUserIdList(
            @Param("scriptId") Long scriptId
    );

}
