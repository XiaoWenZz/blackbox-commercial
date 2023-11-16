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
@ApiModel("UpdateBannerRequest 更新轮播图请求")
public class UpdateBannerRequest {

    @NotNull
    @ApiModelProperty("轮播图id")
    private Long bannerId;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("优先级 越大优先级越高")
    private Integer priority;


}
