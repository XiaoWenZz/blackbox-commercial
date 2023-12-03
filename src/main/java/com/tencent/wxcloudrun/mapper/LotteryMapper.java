package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.Lottery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LotteryMapper extends BaseMapper<Lottery> {

    @Select("select * from lottery where exhibition_id=#{exhibitionId}")
    List<Lottery> getLotteryByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select * from lottery where user_id=#{userId} and exhibition_id=#{exhibitionId}")
    Lottery getLotteryByUserIdAndExhibitionId(
            @Param("userId") Long userId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select * from lottery where user_id=#{userId}")
    List<Lottery> getLotteryListByUserId(
            @Param("userId") Long userId
    );
}
