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
@ApiModel("BriefCommentLandScript 讨论地剧本")
public class BriefCommentLandScript {

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("展会展本id")
    private Long exhibitScriptId;

    @ApiModelProperty("剧本名称")
    private String name;

    @ApiModelProperty("剧本封面")
    private String cover;

    @ApiModelProperty("展本讨论度")
    private Integer discussCount;


}
