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
@ApiModel("SubComment 评论的子评论")
public class SubComment {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("子评论id")
    private Long id;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("评论用户id")
    private Long commentUserId;

    

}
