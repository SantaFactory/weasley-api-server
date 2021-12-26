package com.weasleyclock.weasley.config

import org.h2.tools.Server
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.sql.SQLException

@Profile("local")
@Configuration
class H2Configuration {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): HikariDataSource {
        defaultRun()
        return HikariDataSource()
    }

    @Throws(SQLException::class)
    private fun defaultRun(): Server {
        return Server.createTcpServer(
            "-tcp",
            "-tcpAllowOthers",
            "-ifNotExists",
            "-tcpPort", 9095.toString() + ""
        ).start()
    }

}
