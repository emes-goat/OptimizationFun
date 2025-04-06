plugins {
    kotlin("jvm") version "2.1.10"
    id("me.champeau.jmh") version "0.7.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.openjdk.jmh:jmh-core:1.37")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

jmh {
    jvmArgs = listOf<String>("-Xmx6G")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}