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
@ApiModel("ExhibitScript 展会展本")
public class ExhibitScript {
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("剧本id")
    private Long id;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("首发情况 0非首发 1首发")
    private Integer debutFlag;

    @ApiModelProperty("展本房间")
    private String room;

    @ApiModelProperty("已通知")
    private Integer notified;

    @ApiModelProperty("讨论度")
    private Integer discussCount;

}
