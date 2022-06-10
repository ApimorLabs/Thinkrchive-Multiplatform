plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
}

kotlin {
    jvm()
    js(IR) {
        binaries.executable()
        useCommonJs()
        browser()
    }

    sourceSets["commonMain"].dependencies {
        implementation(Dependencies.Kotlin.serializationCore)
    }
}
