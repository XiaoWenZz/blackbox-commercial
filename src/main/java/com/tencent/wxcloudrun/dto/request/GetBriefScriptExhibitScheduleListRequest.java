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
@ApiModel("GetBriefScriptExhibitScheduleListRequest 获取简要剧本参展日程列表请求")
public class GetBriefScriptExhibitScheduleListRequest {

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;


}
