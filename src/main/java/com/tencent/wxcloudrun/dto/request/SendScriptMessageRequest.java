package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SendPreviewMessageRequest 发送订阅预告消息请求")
public class SendScriptMessageRequest {

    @NotNull
    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @ApiModelProperty("page")
    private String page;

    @ApiModelProperty("lang")
    private String lang;

    @ApiModelProperty("miniprogramState")
    private String miniprogramState;

    @ApiModelProperty("character_string1's value")
    private String value1;

    @ApiModelProperty("thing2's value")
    private String value2;

    @ApiModelProperty("thing3's value")
    private String value3;

}
