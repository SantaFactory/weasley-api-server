import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.allopen") version "1.4.32"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "com.weasleyclock"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://repo.spring.io/release") }
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
//    implementation("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2")
    runtimeOnly("com.h2database:h2")
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
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<BootBuildImage> {
    builder = "paketobuildpacks/builder:tiny"
    environment = mapOf("BP_NATIVE_IMAGE" to "true")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

if (project.hasProperty("prod")) {
}
