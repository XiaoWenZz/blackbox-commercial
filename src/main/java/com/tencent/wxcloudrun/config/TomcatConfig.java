package com.tencent.wxcloudrun.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class TomcatConfig {

//    @Value("${spring.server.MaxFileSize}")
//    private String MaxFileSize;
//    @Value("${spring.server.MaxRequestSize}")
//    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.parse("5000MB")); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse("5000MB"));
        return factory.createMultipartConfig();
    }
}