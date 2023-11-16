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
@ApiModel("BriefExhibition 展会简要信息")
public class BriefExhibition {

    @ApiModelProperty("展会id")
    private Long id;

    @ApiModelProperty("展会名称")
    private String name;

    @ApiModelProperty("展会地点")
    private String place;

    @ApiModelProperty("展会开始时间")
    private String startTime;

    @ApiModelProperty("展会结束时间")
    private String endTime;

    @ApiModelProperty("展会状态 0未开始 1进行中 2已结束")
    private Integer status;

    @ApiModelProperty("展会剧本数")
    private Integer scriptNum;

}
