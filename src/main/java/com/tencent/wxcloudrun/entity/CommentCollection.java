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
@ApiModel("CommentCollection 评论收藏")
public class CommentCollection {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("评论收藏id")
    private Long id;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("评论收藏时间")
    private String createTime;


}
