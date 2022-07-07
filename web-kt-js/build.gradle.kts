val kotlinVersion = "1.6.21"
val serializationVersion = "1.3.3"
val reactVersion = "18.0.0-pre.332-kotlin-1.6.21"
val kotlinEmotion = "11.9.0-pre.332-kotlin-1.6.21"
val coroutinesVersion = "1.6.1" // See: Kotlin/kotlinx.coroutines#3305
val reactYtLiteVersion = "1.0.4"
val reactShareVersion = "4.4.0"
val kotlinStyleVersion = "5.3.5-pre.341-compat"

plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":common:all-features"))
    implementation(project(":common:network"))
    implementation(project(":common:model"))
    implementation(project(":common:mvvm-core"))
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:$kotlinEmotion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

tasks.create("stage") {
    dependsOn(tasks.getByName("build"))
}