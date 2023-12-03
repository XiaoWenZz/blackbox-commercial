package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.Preview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PreviewMapper extends BaseMapper<Preview> {

    @Select("SELECT count(id) FROM preview WHERE time_slot=#{timeSlot} AND exhibition_date_id=#{exhibitionDateId} and exhibition_id=#{exhibitionId}")
    Integer getPreviewCount(
            @Param("timeSlot") Integer timeSlot,
            @Param("exhibitionDateId") Integer exhibitionDateId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("SELECT * from preview WHERE status = 1 AND exhibition_id=#{exhibitionId}")
    List<Preview> getRunningPreviews(
            @Param("exhibitionId") Long exhibitionId);

    @Select("SELECT * from preview WHERE exhibition_id=#{exhibitionId}")
    List<Preview> getPreviewsByExhibitionId(
            @Param("exhibitionId") Long exhibitionId);

    @Select("SELECT * from preview WHERE exhibition_date_id=#{exhibitionDateId} and exhibition_id=#{exhibitionId}")
    List<Preview> getPreviewsByExhibitionDateId(
            @Param("exhibitionDateId") Integer exhibitionDateId,
            @Param("exhibitionId") Long exhibitionId);
}
