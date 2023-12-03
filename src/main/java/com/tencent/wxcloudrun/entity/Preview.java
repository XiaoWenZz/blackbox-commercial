package com.tencent.wxcloudrun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Preview 公车预告")
public class Preview {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("预告id")
    private Long id;

    @ApiModelProperty("发行")
    private Long merchantId;

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

    @ApiModelProperty("展示开始时间")
    private String startTime;

    @ApiModelProperty("展示结束时间")
    private String endTime;

    @ApiModelProperty("展示状态 0未开始 1已开始 2已结束 3被取消")
    private Integer status;

    @ApiModelProperty("展会日期id（展会第x天）")
    private Integer exhibitionDateId;

}
