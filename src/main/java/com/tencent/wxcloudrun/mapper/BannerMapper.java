package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerMapper extends BaseMapper<Banner> {

    @Select("select * from banner")
    List<Banner> getBannerList();

    @Select("select * from banner where exhibition_id = #{exhibitionId} order by priority desc")
    List<Banner> getBannerListByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select * from banner where script_id = #{scriptId} order by priority desc")
    List<Banner> getBannerListByScriptId(
            @Param("scriptId") Long scriptId
    );

    @Select("select * from banner where script_id is not null order by priority desc")
    List<Banner> getHomepageScriptBannerList();

    @Select("select * from banner where exhibition_id is not null order by priority desc")
    List<Banner> getHomepageExhibitionBannerList();

}
