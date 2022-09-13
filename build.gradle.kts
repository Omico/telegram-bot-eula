plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.omico.telegram.bot.eula"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = listOf(
                    "-Xcontext-receivers",
                )
            }
        }
    }
}

dependencies {
    implementation("eu.vendeli:telegram-bot:2.2.1")
    implementation("io.ktor:ktor-client-core:2.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(kotlin("test"))
}
