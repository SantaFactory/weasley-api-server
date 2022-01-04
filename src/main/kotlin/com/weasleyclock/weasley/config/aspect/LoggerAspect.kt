package com.weasleyclock.weasley.config.aspect

import com.weasleyclock.weasley.utils.SecurityUtils
import mu.KotlinLogging
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggerAspect {

    private val log = KotlinLogging.logger {}

    @Around("within(com.weasleyclock.weasley.web.*)")
    fun beforeAdvice(){
        log.info { "login=${SecurityUtils.getCurrentLoginUserName()}|" }
    }

}
