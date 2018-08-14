package com.generator.run;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * * @Description: Swagger2
 * 
 * @author Jack
 * @date 2017年9月19日 下午2:51:38
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2
{
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.generator"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("代码生成器").description("轻松实现entity、mapper文件等")
                .contact("李景辉")
                .build();
    }
}
