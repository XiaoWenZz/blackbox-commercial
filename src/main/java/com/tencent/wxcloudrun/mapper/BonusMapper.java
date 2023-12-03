package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.Bonus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BonusMapper extends BaseMapper<Bonus> {

    @Select("select * from bonus")
    List<Bonus> getBonusList();

    @Select("select * from bonus where exhibition_id=#{exhibitionId}")
    List<Bonus> getBonusListByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );


    @Select("select code from bonus")
    List<String> getCodes();

    @Select("select * from bonus where code=#{code}")
    Bonus getBonusByCode(
            @Param("code") String code
    );

}
