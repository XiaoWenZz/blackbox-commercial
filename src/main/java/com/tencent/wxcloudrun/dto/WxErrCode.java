package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxErrCode {

    private Integer errcode;
    private String errmsg;

}