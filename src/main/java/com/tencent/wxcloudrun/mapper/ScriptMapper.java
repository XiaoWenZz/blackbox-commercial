package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.BriefScript;
import com.tencent.wxcloudrun.entity.Script;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptMapper extends BaseMapper<Script> {
    @Select("SELECT * FROM script")
    List<BriefScript> getAllScriptList();

    @Select("SELECT * FROM script WHERE type = #{type} AND sale_form = #{saleForm}")
    List<BriefScript> getBriefScriptListByTypeAndSaleForm(
            @Param("type") Integer type,
            @Param("saleForm") Integer saleForm);

    @Select("SELECT * FROM script WHERE type = #{type}")
    List<BriefScript> getBriefScriptListByType(@Param("type") Integer type);

    @Select("SELECT * FROM script WHERE sale_form = #{saleForm}")
    List<BriefScript> getBriefScriptListBySaleForm(@Param("saleForm") Integer saleForm);
}
