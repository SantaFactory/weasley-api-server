package com.weasleyclock.weasleyclient.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class DateTimeFormatConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {

        val dateTimeFormatterRegistrar = DateTimeFormatterRegistrar()

        dateTimeFormatterRegistrar.setUseIsoFormat(true)

        dateTimeFormatterRegistrar.registerFormatters(registry)
    }

}
