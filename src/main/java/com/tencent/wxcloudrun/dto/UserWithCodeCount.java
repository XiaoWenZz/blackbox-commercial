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
@ApiModel("UserWithCodeCount 用户及其抽奖码数量")
public class UserWithCodeCount {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户抽奖码数量")
    private Integer codeCount;

    @ApiModelProperty("排名")
    private Integer rank;

}
