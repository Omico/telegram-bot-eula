import java.util.Properties

plugins {
    application
    kotlin("jvm") version "1.8.10"
}

group = "me.omico.telegram.bot.eula"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(18)
    target {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xcontext-receivers",
                )
            }
        }
    }
}

application {
    applicationName = "EulaBot"
    mainClass.set("me.omico.telegram.bot.eula.EulaBot")
}

dependencies {
    val ktorVersion = "2.2.3"
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("eu.vendeli:telegram-bot:2.5.4") {
        exclude(group = "io.ktor")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(kotlin("test"))
}

val localProperties by lazy {
    Properties().apply {
        val localPropertiesFile = rootProject.file("local.properties")
        if (!localPropertiesFile.exists()) localPropertiesFile.createNewFile()
        localPropertiesFile.inputStream().use(::load)
    }
}

tasks.run<JavaExec> {
    args = listOf("--token", localProperties["BOT_TOKEN"] as String)
}
