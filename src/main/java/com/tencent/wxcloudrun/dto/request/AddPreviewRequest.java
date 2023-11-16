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
@ApiModel("AddPreviewRequest 添加公车预告请求")
public class AddPreviewRequest {

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("剧本名称")
    private String scriptName;

    @ApiModelProperty("剧本封面")
    private String scriptCover;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("展酒房间")
    private String exhibitionRoom;

    @ApiModelProperty("预告内容")
    private String content;

    @ApiModelProperty("展示时间段")
    private Integer timeSlot;

    @NotNull
    @ApiModelProperty("展示开始时间 格式如 2007-12-03T10:15:30")
    private String startTime;

    @NotNull
    @ApiModelProperty("展示结束时间 格式同上")
    private String endTime;

    @ApiModelProperty("展会日期id（展会第x天）")
    private Integer exhibitionDateId;
}
