package com.tencent.wxcloudrun.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.config.YmlConfig;
import com.tencent.wxcloudrun.dto.request.GetUrlSchemeRequest;
import com.tencent.wxcloudrun.dto.request.UniformOrderRequest;
import com.tencent.wxcloudrun.service.UrlService;
import com.tencent.wxcloudrun.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YmlConfig ymlConfig;

    @Autowired
    private SessionUtils sessionUtils;

    /**
     * 获取小程序的URL Scheme
     * @param request 请求参数
     * @return 生成的URL Scheme，如 'weixin://wxapkg/<base64(app_id+path)>'
     */
    @Override
    public String getUrlScheme(GetUrlSchemeRequest request) {

        // 构造请求URL
        String url = "https://api.weixin.qq.com/wxa/generatenfcscheme";

        // 构造请求参数
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, Object> jumpWxa = new HashMap<>();
        jumpWxa.put("path", request.getPath());
        jumpWxa.put("query", request.getQuery());
        jumpWxa.put("env_version", request.getEnvVersion());

        requestBody.put("jump_wxa", jumpWxa);
        requestBody.put("sn", request.getSn());
        requestBody.put("model_id", CommonConstants.MODEL_ID);

        // 发送POST请求
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestBody, byte[].class);

        // 解析响应结果
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            byte[] responseBody = responseEntity.getBody();
            assert responseBody != null;
            return new String(responseBody, StandardCharsets.UTF_8);
        } else {
            throw new RuntimeException("Failed to get URL scheme, status code: " + responseEntity.getStatusCodeValue());
        }
    }

    @Override
    public String order(UniformOrderRequest request) {

        String url = "http://api.weixin.qq.com/_/pay/unifiedorder";

        // 创建请求体参数
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("body", request.getBody());
        requestBody.put("openid", sessionUtils.getOpenId());
        requestBody.put("out_trade_no", request.getOutTradeNo());
        requestBody.put("spbill_create_ip", request.getSpbillCreateIp());
        requestBody.put("env_id", CommonConstants.ENV);
        requestBody.put("sub_mch_id", CommonConstants.MERCHANT_ID);
        requestBody.put("total_fee", request.getTotalFee());
        requestBody.put("callback_type", 2);

        Map<String, Object> container = new HashMap<>();
        container.put("service", request.getService());
        container.put("path", request.getPath());
        requestBody.put("container", container);

        // 发送POST请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestBody, String.class);

        // 获取响应结果
        return responseEntity.getBody();

    }

    @Override
    public String checkOrder(String outTradeNo) {

        String url = "http://api.weixin.qq.com/_/pay/queryorder";
        // 创建请求体参数
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("out_trade_no", outTradeNo);
        requestBody.put("sub_mch_id", CommonConstants.MERCHANT_ID);

        // 发送POST请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestBody, String.class);
        return responseEntity.getBody();

    }

    @Override
    public Map<String, String> getTmpAuth() {
        String url = "http://api.weixin.qq.com/_/cos/getauth";
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        return (Map<String, String>) responseEntity.getBody();
    }

    @Override
    public COSClient getCosClient() {
        Map<String,String> responseBody = getTmpAuth();
        String secretId = responseBody.get("TmpSecretId");
        String secretKey = responseBody.get("TmpSecretKey");
        String token = responseBody.get("Token");
        String region = CommonConstants.COS_REGION;

        try {
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            COSCredentials cosCredentials = new BasicSessionCredentials(secretId, secretKey, token);
            return new COSClient(cosCredentials, clientConfig);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get COS client, error: " + e.getMessage());
        }

    }


    /**
     * 获取access_token
     * @return access_token
     */
    @Override
    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + ymlConfig.getAppId() + "&secret=" + ymlConfig.getAppSecret();
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        Map<String, String> responseBody = responseEntity.getBody();
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseBody.containsKey("access_token")) {
            return responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to get access_token, status code: " + responseEntity.getStatusCodeValue());
        }
    }

}
