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
@ApiModel("CommentLike 评论点赞")
public class CommentLike {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("点赞id")
    private Long id;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("点赞人openId")
    private String userOpenId;

    @ApiModelProperty("点赞时间")
    private String createTime;

}
