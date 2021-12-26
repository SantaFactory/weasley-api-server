package com.weasleyclock.weasley.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class JpaConfiguration {

    @Bean
    fun domainAuditorAware(): DomainAuditorAware = DomainAuditorAware()

}