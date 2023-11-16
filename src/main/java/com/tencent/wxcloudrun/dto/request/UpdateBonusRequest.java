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
@ApiModel("UpdateBonusRequest 更新奖品信息")
public class UpdateBonusRequest {

    @ApiModelProperty("奖品id")
    private Long id;

    @ApiModelProperty("奖品名称")
    private String name;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("奖品图片")
    private String image;

    @ApiModelProperty("奖品等级")
    private Integer level;

}
