import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("org.springframework.experimental.aot") version "0.11.0"
}

group = "com.weasleyclock"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://repo.spring.io/release") }
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    //logger
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:1.17")
    //Mysql
    implementation("mysql:mysql-connector-java")
    //JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //H2
    implementation("com.h2database:h2")
    runtimeOnly("com.h2database:h2")
    implementation("org.apache.tomcat:tomcat-jdbc:7.0.19")
    // utils
    implementation("org.apache.commons:commons-lang3:3.9")

    implementation("org.springframework.boot:spring-boot-starter-security")

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
