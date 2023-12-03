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
@ApiModel("Bonus 奖品")
public class Bonus {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("奖品id")
    private Long id;

    @ApiModelProperty("奖品名称")
    private String name;

    @ApiModelProperty("奖品图片")
    private String image;

    @ApiModelProperty("奖品等级")
    private Integer level;

    @ApiModelProperty("奖品对应抽奖码")
    private String code;

    @ApiModelProperty("奖品对应展会id")
    private Long exhibitionId;
}
