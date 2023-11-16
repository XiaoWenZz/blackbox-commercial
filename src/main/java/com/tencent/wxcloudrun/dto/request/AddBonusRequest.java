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
@ApiModel("AddBonusRequest 添加奖品请求")
public class AddBonusRequest {

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("奖品名称")
    private String name;

    @ApiModelProperty("奖品图片")
    private String image;

    @ApiModelProperty("奖品等级")
    private Integer level;

}
