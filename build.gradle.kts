plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
    application
}

group = "com.coreai.iality"
version = "0.0.1"

application {
    mainClass.set("com.coreai.iality.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core-jvm:3.0.3")
    implementation("io.ktor:ktor-server-netty-jvm:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:3.0.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    implementation("ch.qos.logback:logback-classic:1.5.18")

    implementation("org.postgresql:postgresql:42.7.5")

    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")

    implementation("com.zaxxer:HikariCP:6.2.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    implementation("org.eclipse.angus:angus-mail:2.0.4")
    implementation("org.eclipse.angus:angus-activation:2.0.2")

    testImplementation("io.ktor:ktor-server-test-host-jvm:3.0.3")
    testImplementation(kotlin("test"))
}