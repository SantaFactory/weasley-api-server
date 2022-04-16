plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("kapt")
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
}
