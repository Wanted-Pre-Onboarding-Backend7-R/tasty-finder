package com.wanted.teamr.tastyfinder.api.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDocConfig {

    private final SpringDocConfigProperties springDocConfigProperties;

    /**
     * swagger ui 웹 페이지 주소: /swagger-ui/index.html
     * <p>
     * swagger ui 웹 페이지 리소스(js, css 등) 주소: /swagger-ui/**
     *
     * @return swagger-ui 웹 페이지, 웹 페이지를 위한 리소스 모두를 포함하는 주소 반환
     */
    @Bean(name = "swaggerPath")
    public String getSwaggerPath() {
        return "/swagger-ui/**";
    }

    /**
     * api doc 주소: configuraiton file의 {springdoc.api-docs.path}
     * <p>
     * swagger-ui에서 요청하는 리소스(swagger-config 등) 주소: {springdoc.api-docs.path}/**
     *
     * @return api doc 주소와 그 외 swagger-ui에서 요청하는 리소스 모두를 포함하는 주소 반환
     */
    @Bean(name = "apiDocPath")
    public String getApiDocPath() {
        return springDocConfigProperties.getApiDocs().getPath() + "/**";
    }

}
