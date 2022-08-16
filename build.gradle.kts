import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    kotlin("plugin.jpa") version "1.7.10"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    id("org.springframework.boot") version "2.7.2"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"
    id("com.netflix.dgs.codegen") version "5.2.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:5.0.5")

    implementation("org.springframework.boot:spring-boot-dependencies:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-tomcat")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-shared-core:5.2.6")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-validation")
    implementation("com.netflix.graphql.dgs:graphql-dgs")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.2")
}

tasks.test {
    useJUnitPlatform()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClient = true
    generateDataTypes = true
    generateKotlinNullableClasses = true // IsRequired as a field does not work with this option

    doFirst {
        packageName = "${project.group}.dgs.generated"
        schemaPaths = mutableListOf("${project.projectDir}/src/main/resources/graphql")
    }
}
