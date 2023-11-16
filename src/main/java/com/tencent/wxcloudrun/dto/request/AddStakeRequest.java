package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AddStakeRequest 新增发布")
public class AddStakeRequest {
    @ApiModelProperty("预约发布开始时间")
    private String startTime;

    @ApiModelProperty("预约发布结束时间")
    private String endTime;

    @ApiModelProperty("额度数")
    private Integer quota;
}
