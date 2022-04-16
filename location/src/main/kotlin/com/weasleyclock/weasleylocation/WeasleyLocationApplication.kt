package com.weasleyclock.weasleylocation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeasleyLocationApplication

fun main(args: Array<String>) {
    runApplication<WeasleyLocationApplication>(*args)
}
