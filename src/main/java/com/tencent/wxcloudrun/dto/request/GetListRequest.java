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
@ApiModel("GetListRequest 获取列表请求")
public class GetListRequest {

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;


}
