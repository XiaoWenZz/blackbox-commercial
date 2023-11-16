package com.tencent.wxcloudrun.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BriefCommentCollection 收藏评论")
public class BriefCommentCollection {

    @ApiModelProperty("评论收藏id")
    private Long id;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论用户id")
    private Long commentUserId;

    @ApiModelProperty("评论用户昵称")
    private String commentUserNickname;

    @ApiModelProperty("评论用户头像")
    private String commentUserPortrait;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论时间")
    private String commentTime;

    @ApiModelProperty("评论点赞量")
    private Integer likeCount;

    @ApiModelProperty("评论收藏量")
    private Integer collectionCount;

    @ApiModelProperty("子评论数")
    private Integer subCommentCount;

    @ApiModelProperty("评论收藏时间")
    private String createTime;


}
