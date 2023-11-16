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
@ApiModel("BriefScriptExhibitSchedule 简要剧本参展日程")
public class BriefScriptExhibitSchedule {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("参与展会id")
    private Long exhibitionId;

    @ApiModelProperty("展会名称")
    private String name;

    @ApiModelProperty("展会地点")
    private String place;

    @ApiModelProperty("展会开始时间")
    private String startTime;

    @ApiModelProperty("展会结束时间")
    private String endTime;


}
