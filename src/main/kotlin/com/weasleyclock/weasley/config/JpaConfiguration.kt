package com.weasleyclock.weasley.config

import com.querydsl.jpa.impl.JPAQueryFactory
import com.weasleyclock.weasley.config.security.DomainAuditorAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
@EnableJpaAuditing
class JpaConfiguration(@PersistenceContext val entityManager: EntityManager) {

    @Bean
    fun domainAuditorAware(): DomainAuditorAware = DomainAuditorAware()

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}
