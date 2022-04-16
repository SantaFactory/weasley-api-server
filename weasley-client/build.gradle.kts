plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("kapt")
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation("com.auth0:jwks-rsa:0.3.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.infinispan:infinispan-core:12.0.1.Final")
    implementation("org.infinispan:infinispan-commons:12.0.1.Final")
    implementation("org.infinispan:infinispan-marshaller-protostuff:12.0.1.Final")
    implementation("org.jboss.marshalling:jboss-marshalling-osgi:2.0.10.Final")
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
