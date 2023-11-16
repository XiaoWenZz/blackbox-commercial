package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.BriefMerchant;
import com.tencent.wxcloudrun.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {

    @Select("select id from merchant where admin_key = #{adminKey}")
    Long getMerchantIdByAdminKey(
            @Param("adminKey") String adminKey
    );

    @Select("select id from merchant where member_key = #{memberKey}")
    Long getMerchantIdByMemberKey(
            @Param("memberKey") String memberKey
    );

    @Select("select id from merchant where admin_key = #{key} or member_key = #{key}")
    Long getMerchantByKey(
            @Param("key") String key
    );

    @Select("select * from merchant where id = #{id}")
    BriefMerchant getBriefMerchantById(
            @Param("id") Long id
    );
}
