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
@ApiModel("AddExhibitScriptRequest 添加展会展本")
public class AddExhibitScriptRequest {

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("首发情况 0非首发 1首发")
    private Integer debutFlag;

    @ApiModelProperty("展本房间")
    private String room;

}
