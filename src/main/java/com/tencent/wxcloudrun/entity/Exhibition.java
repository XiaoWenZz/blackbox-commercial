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
@ApiModel("Exhibition 展会")
public class Exhibition {
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("展会id")
    private Long id;

    @ApiModelProperty("展会名称")
    private String name;

    @ApiModelProperty("展会简称")
    private String exhibitionAbbreviation;

    @ApiModelProperty("展会地点")
    private String place;

    @ApiModelProperty("展会开始时间")
    private String startTime;

    @ApiModelProperty("展会结束时间")
    private String endTime;

    @ApiModelProperty("开奖时间")
    private String lotteryTime;

    @ApiModelProperty("联系方式")
    private String contactUrl;

    @ApiModelProperty("展会状态 0未开始 1进行中 2已结束")
    private Integer status;

    @ApiModelProperty("展会海报")
    private String poster;

}
