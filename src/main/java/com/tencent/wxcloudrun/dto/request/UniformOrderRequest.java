package com.tencent.wxcloudrun.dto.request;

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
@ApiModel("UniformOrderRequest 统一下单请求")
public class UniformOrderRequest {

    @ApiModelProperty("商品描述")
    private String body;

    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    @ApiModelProperty("终端IP")
    private String spbillCreateIp;

    @ApiModelProperty("订单总金额，单位为分")
    private Integer totalFee;

    @ApiModelProperty("服务名")
    private String service;

    @ApiModelProperty("路径")
    private String path;

}
