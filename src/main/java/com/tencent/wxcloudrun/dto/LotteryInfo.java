package com.tencent.wxcloudrun.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("LotteryInfo 开奖信息")
public class LotteryInfo {
    @ApiModelProperty("开奖id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("所属发行/店家名称")
    private String publisherName;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("用户类型 系统管理员为null 0为店家 1为发行成员 2为发行管理员")
    private Integer userType;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("抽奖码")
    private String code;

    @ApiModelProperty("奖品id")
    private Long bonusId;

    @ApiModelProperty("奖品名称")
    private String bonusName;

    @ApiModelProperty("展会id")
    private Long exhibitionId;


}
