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
@ApiModel("BriefMerchant 发行商列表")
public class BriefMerchant {

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

}
