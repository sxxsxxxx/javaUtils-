package com.sunUtils.commos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select().build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Sx", "", "sun_xiao@cdv.com");
        return new ApiInfo("多数据源接口",//大标题 title
                "云图DataSources",//小标题
                "0.0.1",//版本
                "",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                ""//网站链接)
        );

    }
}
