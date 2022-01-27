package com.weasleyclock.weasleyclient.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket


/**
 * Swagger Configuration
 * @About https://bcp0109.tistory.com/326
 * @Url http://localhost:5050/swagger-ui/index.html#
 */

@EnableWebMvc
@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): Docket? = Docket(DocumentationType.OAS_30)
        .useDefaultResponseMessages(false)
        .securityContexts(listOf(securityContext()))
        .securitySchemes(listOf(apiKey()) as List<SecurityScheme>?)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.weasleyclock.weasley.web"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())

    private fun apiInfo(): ApiInfo? = ApiInfoBuilder()
        .title("Weasley Docs")
        .description("weasley api docs")
        .version("1.0")
        .build()

    private fun securityContext(): SecurityContext? = SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build()

    private fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

    private fun apiKey(): ApiKey? = ApiKey("Authorization (Bearer + token)", "Authorization", "header")

}
