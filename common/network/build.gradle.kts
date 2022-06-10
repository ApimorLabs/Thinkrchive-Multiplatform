import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.kotlin
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

kotlin {
    android()
    jvm("desktop")
    js(IR) {
        binaries.executable()
        useCommonJs()
        browser()
    }

    sourceSets["commonMain"].dependencies {
        implementation(project(":common:model"))

        with(Dependencies.Ktor) {
            api(ktorCore) // Referenced as api so we can access their Exceptions in other modules
            api(json) // Referenced as api so we can access their Exceptions in other modules
            implementation(contentNegotiation)
            implementation(ktorLogging)
        }

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }

        with(Dependencies.Log) {
            api(kermit)
        }
    }

    sourceSets["commonTest"].dependencies {
        implementation(Dependencies.Koin.test)
        implementation(Dependencies.Kotlin.Coroutines.test)
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
    }

    sourceSets["androidMain"].dependencies {
        implementation(Dependencies.Ktor.ktorAndroidEngine)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Dependencies.Android.junit)
    }

    sourceSets["desktopMain"].dependencies {
        implementation(Dependencies.Ktor.ktorJavaEngine)
        implementation(Dependencies.Log.slf4j)
    }

    sourceSets["jsMain"].dependencies {
        implementation(Dependencies.Ktor.jsEngine)
    }

    sourceSets["jsTest"].dependencies {

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
