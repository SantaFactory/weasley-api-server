import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "2.6.3" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    kotlin("jvm") version "1.6.10" apply false
    kotlin("plugin.spring") version "1.6.10" apply false
    kotlin("plugin.jpa") version "1.6.10" apply false
}

repositories {
    mavenCentral()
}

allprojects {

    val javaVersion = "11"

    group = "com.weasleyclock"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
        implementation("org.apache.commons:commons-lang3:3.9")
        testImplementation("io.mockk:mockk:1.12.0")
    }
}

project("core") {
    dependencies {
    }
}

project("client") {

    dependencies {
        implementation(project(":core"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.boot:spring-boot-starter-mustache")
        runtimeOnly("org.springframework.boot:spring-boot-devtools")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
        implementation("com.h2database:h2")
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
        implementation("com.auth0:jwks-rsa:0.3.0")
        implementation("io.jsonwebtoken:jjwt:0.9.1")
        implementation("org.infinispan:infinispan-core:12.0.1.Final")
        implementation("org.infinispan:infinispan-commons:12.0.1.Final")
        implementation("org.infinispan:infinispan-marshaller-protostuff:12.0.1.Final")
        implementation("org.jboss.marshalling:jboss-marshalling-osgi:2.0.10.Final")
        implementation("com.querydsl:querydsl-jpa")

    }
}

project("location") {
    dependencies {
        implementation(project(":core"))
    }
}
