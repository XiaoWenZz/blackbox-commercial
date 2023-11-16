package com.tencent.wxcloudrun.dto.request;

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
@ApiModel("AddCommentRequest 添加评论请求")
public class AddCommentRequest {

    @ApiModelProperty("展会展本id")
    private Long exhibitScriptId;

    @ApiModelProperty("评分")
    private float score;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论图片")
    private String image;

    @ApiModelProperty("是否匿名 1否 2是")
    private Integer isAnonymous;



}
