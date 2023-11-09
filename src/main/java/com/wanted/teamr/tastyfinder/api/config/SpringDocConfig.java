package com.wanted.teamr.tastyfinder.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDocConfig {

    private final SpringDocConfigProperties springDocConfigProperties;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

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

    @Bean(name = "customSwaggerPath")
    public String getCustomSwaggerPath() {
        return swaggerUiConfigProperties.getPath();
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

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("지리기반 맛집 추천 웹 서비스 API")
                .description("본 서비스는 지리기반 맛집 추천 웹서비스입니다.</br>" +
                        "사용자는 사용자 위치 기반으로 맛집을 추천 받고 해당 맛집의 상세 정보를 확인할 수 있습니다.")
                .version("1.0.0");
    }

}
