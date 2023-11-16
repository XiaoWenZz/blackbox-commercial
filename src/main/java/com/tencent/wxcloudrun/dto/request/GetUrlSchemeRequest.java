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
@ApiModel("GetUrlSchemeRequest 获取URL Scheme请求")
public class GetUrlSchemeRequest {

    @ApiModelProperty("path")
    private String path;

    @ApiModelProperty("query")
    private String query;

    @ApiModelProperty("envVersion")
    private String envVersion;

    @ApiModelProperty("sn")
    private String sn;

}
