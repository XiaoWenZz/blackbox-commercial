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
@ApiModel("SweepStake 用户抽奖信息")
public class SweepStake {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("发行id")
    private Long merchantId;

    @ApiModelProperty("发布id")
    private Long stakeId;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("类型 0为官方 1为发行发布")
    private Integer type;

    @ApiModelProperty("抽奖时间")
    private String createTime;

    @ApiModelProperty("兑奖码数量")
    private Integer codeNum;

    @ApiModelProperty("兑奖码1")
    private String code1;

    @ApiModelProperty("兑奖码2")
    private String code2;

    @ApiModelProperty("兑奖码3")
    private String code3;

}
