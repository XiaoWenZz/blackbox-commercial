package com.tencent.wxcloudrun.dto.request;

import com.tencent.wxcloudrun.entity.User;
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
@ApiModel("UpdateUserRequest 更新用户信息")
public class UpdateUserRequest {
    /**
     * {@link User}
     */

    @ApiModelProperty("电话号")
    private String telephone;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("发行/店家名称")
    private String publisherName;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("芯片id")
    private String chipId;

    @ApiModelProperty("微信号")
    private String wechatId;

    @ApiModelProperty("头像")
    private String portrait;


}
