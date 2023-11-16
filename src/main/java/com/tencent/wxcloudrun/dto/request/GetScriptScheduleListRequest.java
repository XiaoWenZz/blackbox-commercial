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
@ApiModel("GetScriptScheduleListRequest 获取参展日程列表请求")
public class GetScriptScheduleListRequest {

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @ApiModelProperty("页码")
    private Integer pageNum;


}
