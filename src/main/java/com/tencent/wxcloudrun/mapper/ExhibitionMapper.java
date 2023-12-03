package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.BriefExhibition;
import com.tencent.wxcloudrun.entity.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitionMapper extends BaseMapper<Exhibition> {

    @Select("SELECT id, name, place, start_time, end_time, status FROM exhibition")
    List<BriefExhibition> getExhibitionList();

    @Select("SELECT id, name, place, start_time, end_time, status FROM exhibition where status = #{status}")
    List<BriefExhibition> getExhibitionListByStatus(
            @Param("status") int status
    );

}
