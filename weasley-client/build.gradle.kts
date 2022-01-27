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
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.apache.tomcat:tomcat-jdbc:7.0.19")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation("com.auth0:jwks-rsa:0.3.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.infinispan:infinispan-core:12.0.1.Final")
    implementation("org.infinispan:infinispan-commons:12.0.1.Final")
    implementation("org.infinispan:infinispan-marshaller-protostuff:12.0.1.Final")
    implementation("org.jboss.marshalling:jboss-marshalling-osgi:2.0.10.Final")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.postgresql:postgresql:42.2.23")
    implementation("com.querydsl:querydsl-jpa")
    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    sourceSets.main {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("$buildDir/generated/source/kapt/main")
        }
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
}
