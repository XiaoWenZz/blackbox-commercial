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
@ApiModel("AddSubCommentRequest 添加子评论请求")
public class AddSubCommentRequest {

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论内容")
    private String content;


}
