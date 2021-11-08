package com.example.demo.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger使用的配置文件
 */
@Configuration
@EnableSwagger2
/**启动**/
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi(Environment environment) {

        //获取生产环境
        Profiles profiles=Profiles.of("dev","test");
        String[] activeProfiles = environment.getActiveProfiles();
        boolean b = environment.acceptsProfiles(profiles);

        //配置docket的bean实例
        return new Docket(DocumentationType.SWAGGER_2)
                //分组
                .groupName("临床研发部")
                //是否启动swagger2
                .enable(true)
                //配置swagger的api的信息
                .apiInfo(apiInfo())
                //配置接口扫描
                .select()
                //扫描方式的方式。。。1.扫描包   2.扫描注解
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                //扫描路径
                .paths(PathSelectors.any())
                .build();
    }

    //基本信息的配置，信息会在api文档上显示
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("梁登光学习swagger")
                //接口描述
                .description("医院临床研发部相关接口的文档")
                //服务组的URL
                .termsOfServiceUrl("http://localhost:8080/hello")
                .version("1.0")
                .build();
    }
}
