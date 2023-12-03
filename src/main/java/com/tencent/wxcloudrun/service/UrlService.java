package com.tencent.wxcloudrun.service;

import com.qcloud.cos.COSClient;
import com.tencent.wxcloudrun.dto.request.GetUrlSchemeRequest;
import com.tencent.wxcloudrun.dto.request.UniformOrderRequest;

import java.util.Map;

public interface UrlService {

    String getAccessToken();

    String getUrlScheme(GetUrlSchemeRequest request);

    String order(UniformOrderRequest request);

    String checkOrder(String outTradeNo);

    Map<String, String> getTmpAuth();

    COSClient getCosClient();


}
