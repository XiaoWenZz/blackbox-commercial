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
@ApiModel("AddMerchantRequest 添加发行请求")
public class AddMerchantRequest {

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

    @ApiModelProperty("logo")
    private String logo;

}
