package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.BriefMerchantExhibitScript;
import com.tencent.wxcloudrun.entity.ExhibitScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitScriptMapper extends BaseMapper<ExhibitScript> {

    @Select("SELECT script_id FROM exhibit_script WHERE exhibition_id=#{exhibitionId}")
    List<Long> getScriptIdListByExhibitId(
            @Param("exhibitionId") Long exhibitionId);

    @Select("SELECT script_id FROM exhibit_script WHERE exhibition_id=#{exhibitionId} and debut_flag=#{debutFlag}")
    List<Long> getScriptIdListByExhibitIdAndDebutFlag(
            @Param("exhibitionId") Long exhibitionId,
            @Param("debutFlag") Integer debutFlag);

    @Select("SELECT debut_flag FROM exhibit_script WHERE id=#{id} and script_id=#{scriptId}")
    Integer getDebutFlagByExhibitIdAndScriptId(
            @Param("id") Long id,
            @Param("scriptId") Long scriptId);

    @Select("select * from exhibit_script where script_id=#{scriptId} and exhibition_id=#{exhibitionId}")
    ExhibitScript getExhibitScriptByScriptIdAndExhibitionId(
            @Param("scriptId") Long scriptId,
            @Param("exhibitionId") Long exhibitionId);

    @Select("select * from exhibit_script join script on exhibit_script.script_id=script.id where exhibit_script.exhibition_id=#{exhibitionId} and script.merchant1_id=#{merchantId}")
    List<BriefMerchantExhibitScript> getExhibitScriptListByExhibitionIdAndMerchantId(
            @Param("exhibitionId") Long exhibitionId,
            @Param("merchantId") Long merchantId);

    @Select("SELECT count(*) FROM exhibit_script where exhibition_id = #{exhibitionId}")
    Integer getScriptNumByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

}
