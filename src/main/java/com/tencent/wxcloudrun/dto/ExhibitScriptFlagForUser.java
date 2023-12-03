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
@ApiModel("ExhibitScriptFlagForUser 展本插旗情况")
public class ExhibitScriptFlagForUser {

    @ApiModelProperty("用户插旗情况")
    private Integer attitude;

    @ApiModelProperty("展本支持数")
    private Integer supportCount;

    @ApiModelProperty("展本观望数")
    private Integer waitCount;


}
