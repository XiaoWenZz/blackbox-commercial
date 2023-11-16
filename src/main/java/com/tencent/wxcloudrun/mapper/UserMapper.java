package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.BriefUser;
import com.tencent.wxcloudrun.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user")
    List<BriefUser> getBriefUserList();

    @Select("SELECT * FROM user WHERE open_id=#{openId}")
    User getUserByOpenId(@Param("openId")String openId);

    @Select("SELECT * FROM user WHERE chip_id=#{chipId}")
    User getUserByChipId(@Param("chipId")String chipId);


}
