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
@ApiModel("Banner 轮播图")
public class Banner {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("轮播图id")
    private Long id;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("展会id type为1时有")
    private Long exhibitionId;

    @ApiModelProperty("剧本id type为2时有")
    private Long scriptId;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("类型 1为展会banner 2为本单banner")
    private Integer type;

    @ApiModelProperty("优先级 越大优先级越高")
    private Integer priority;

}
