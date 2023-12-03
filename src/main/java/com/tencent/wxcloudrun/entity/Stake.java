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
@ApiModel("Stake 发行抽奖发布信息")
public class Stake {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("发布id")
    private Long id;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("发行id")
    private Long merchantId;

    @ApiModelProperty("发行名称")
    private String merchantName;

    @ApiModelProperty("预约发布开始时间")
    private String startTime;

    @ApiModelProperty("预约发布结束时间")
    private String endTime;

    @ApiModelProperty("额度数")
    private Integer quota;

    @ApiModelProperty("额度剩余数")
    private Integer quotaLeft;

    @ApiModelProperty("预约时间")
    private String createTime;

    @ApiModelProperty("发布状态 0未开始 1进行中 2已结束 3被取消")
    private Integer status;


}
