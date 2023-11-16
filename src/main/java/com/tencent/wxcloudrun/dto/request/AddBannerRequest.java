package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("AddBannerRequest 添加轮播图请求")
public class AddBannerRequest {

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("展会id type为1时有")
    private Long exhibitionId;

    @ApiModelProperty("剧本id type为2时有")
    private Long scriptId;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @NotNull
    @ApiModelProperty("类型 1为展会banner 2为本单banner")
    private Integer type;

    @NotNull
    @ApiModelProperty("优先级 越大优先级越高")
    private Integer priority;


}
