package com.weasleyclock.weasley

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeasleyApplication

fun main(args: Array<String>) {
    runApplication<WeasleyApplication>(*args)
}
