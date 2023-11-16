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
@ApiModel("Merchant 发行")
public class Merchant {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("发行id")
    private Long id;

    @ApiModelProperty("发行号")
    private String merchantNum;

    @ApiModelProperty("发行名称")
    private String name;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("管理员密钥")
    private String adminKey;

    @ApiModelProperty("成员密钥")
    private String memberKey;

    @ApiModelProperty("兑奖额度(仅发行有)")
    private Integer quota;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("可公车预约次数")
    private Integer publishCount;

}
