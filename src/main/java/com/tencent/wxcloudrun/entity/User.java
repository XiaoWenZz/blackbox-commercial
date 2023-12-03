package com.tencent.wxcloudrun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User 用户")
public class User {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("会话id")
    private String sessionId;

    @ApiModelProperty("用户唯一标识")
    private String openId;

    @ApiModelProperty("unionId")
    private String unionId;

    @ApiModelProperty("所属发行/店家id")
    private Long publisherId;

    @ApiModelProperty("所属发行/店家名称")
    private String publisherName;

    @ApiModelProperty("会话密钥")
    private String sessionKey;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("头像")
    private String portrait;

    @ApiModelProperty("身份 0用户 1管理员")
    private Integer type;

    @ApiModelProperty("用户类型 系统管理员为null 0为店家 1为发行成员 2为发行管理员")
    private Integer userType;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("微信号")
    private String wechatId;

    @ApiModelProperty("芯片id")
    private String chipId;

    @ApiModelProperty("是否完成注册抽奖 0未完成 1已完成")
    private Integer isUpdate;

    @ApiModelProperty("商家版openId")
    private String businessOpenId;



    public User(String sessionId, String openId, String unionId, String sessionKey, String createTime,String nickname,
                Integer type, Integer userType, String province, String city,
                String region, String wechatId, String chipId, Integer isUpdate, String businessOpenId) {
        this.sessionId = sessionId;
        this.openId = openId;
        this.unionId = unionId;
        this.sessionKey = sessionKey;
        this.createTime = createTime;
        this.nickname = nickname;
        this.type = type;
        this.userType = userType;
        this.province = province;
        this.city = city;
        this.region = region;
        this.wechatId = wechatId;
        this.chipId = chipId;
        this.isUpdate = isUpdate;
        this.businessOpenId = businessOpenId;
    }

}
