import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
}

group = "io.doubleloop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.arrow-kt:arrow-stack:1.2.1"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    implementation(platform("org.http4k:http4k-bom:5.13.2.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.http4k:http4k-testing-approval:5.13.0.0")
    testImplementation("org.http4k:http4k-testing-hamkrest:5.13.0.0")
    testImplementation("org.http4k:http4k-testing-strikt:5.13.0.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}