package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.Stake;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StakeMapper extends BaseMapper<Stake> {

    @Select("SELECT * FROM stake")
    List<Stake> getStakeList();

    @Select("SELECT count(id) FROM stake WHERE start_time = #{startTime}")
    int getStakeCountByTime(String startTime);

    @Select("SELECT * FROM stake WHERE merchant_id = #{merchantId}")
    List<Stake> getMerchantStakeList(
            @Param("merchantId") Long merchantId);

}
