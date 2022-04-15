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
                implementation(project(":common:persistence:settings"))

                // Feature modules
                implementation(project(":common:features:about"))
                implementation(project(":common:features:settings"))
                implementation(project(":common:features:list"))
                implementation(project(":common:features:details"))
                implementation(project(":common:features:donate"))

                implementation(compose.desktop.currentOs)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.desktop.components.splitPane)
                implementation(compose.materialIconsExtended)

                implementation(Dependencies.OrbitMVI.core)

                implementation(Dependencies.Kotlin.Coroutines.swing)

                with(Dependencies.ArkIvanov.Decompose) {
                    implementation(decompose)
                    implementation(extensionsCompose)
                }

                implementation(Dependencies.Log.kermit)

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

            packageName = "Thinkrchive"
            packageVersion = "1.0.0"
            description = "An Thinkpad archive application"
            copyright = "© 2022 RackaApps. All rights reserved."
            vendor = "RackaApps"

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            windows {
                // Wondering what the heck is this? See : https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "A61E85D8-FA98-417D-9F8E-9B5F1CB2DFDD"
                menuGroup = packageName
                perUserInstall = true
            }
        }
    }
}