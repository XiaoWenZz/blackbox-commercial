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
@ApiModel("ScriptCollection 剧本收藏")
public class ScriptCollection {
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("收藏id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("剧本海报")
    private String picture;
}
