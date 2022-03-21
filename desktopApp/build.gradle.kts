import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common:model"))
                implementation(project(":common:integration"))
                implementation(project(":common:features:about"))
                implementation(project(":common:features:settings"))
                implementation(project(":common:features:list"))
                implementation(project(":common:features:details"))
                implementation(project(":common:features:donate"))

                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.0.0-rc11")

                implementation(Dependencies.OrbitMVI.core)

                implementation(Dependencies.Kotlin.Coroutines.swing)

                with(Dependencies.ArkIvanov.Decompose) {
                    implementation(decompose)
                    implementation(extensionsCompose)
                }

                with(Dependencies.Koin) {
                    api(core)
                    api(test)
                }
            }
        }
        val jvmTest by getting
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "work.racka.thinkrchive.v2.desktop.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jvm"
            packageVersion = "1.0.0"
        }
    }
}