package com.weasleyclock.weasley.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.builders.ApiInfoBuilder

import springfox.documentation.service.ApiInfo

import springfox.documentation.builders.PathSelectors

/**
 * Swagger Configuration
 * @About https://bcp0109.tistory.com/326
 * @Url http://localhost:5050/swagger-ui/index.html#
 */

@EnableWebMvc
@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.weasleyclock.weasley.web"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title("Weasley Docs")
            .description("weasley api docs")
            .version("1.0")
            .build()
    }

}
