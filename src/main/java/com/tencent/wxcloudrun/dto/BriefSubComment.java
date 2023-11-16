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
@ApiModel("BriefSubComment 简要子评论")
public class BriefSubComment {

    @ApiModelProperty("子评论id")
    private Long id;

    @ApiModelProperty("评论者id")
    private Long commentUserId;

    @ApiModelProperty("评论者昵称")
    private String commentUserNickname;

    @ApiModelProperty("评论者头像")
    private String commentUserPortrait;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论时间")
    private String createTime;


}
