plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version Versions.kotlin
}

sqldelight {
    database("ThinkpadDatabase") {
        packageName = "work.racka.thinkrchive.v2.common.database.db"
        sourceFolders = listOf("sqldelight")
    }
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
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

    sourceSets["commonMain"].dependencies {
        with(Dependencies.Ktor) {
            implementation(ktorCore)
            implementation(ktorSerialization)
            implementation(ktorLogging)
            //implementation(contentNegotiation)
            //implementation(json)
        }

        implementation(Dependencies.Kotlin.serializationCore)

        with(Dependencies.Squareup.SQLDelight) {
            implementation(runtime)
            implementation(coroutineExtensions)
        }

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }

        with(Dependencies.Log) {
            api(kermit)
        }
    }

    sourceSets["androidMain"].dependencies {
        implementation(Dependencies.Ktor.ktorAndroidEngine)
        implementation(Dependencies.Squareup.SQLDelight.androidDriver)
    }

    sourceSets["desktopMain"].dependencies {
        implementation(Dependencies.Ktor.ktorJavaEngine)
        implementation(Dependencies.Squareup.SQLDelight.sqliteDriver)
        implementation(Dependencies.Log.slf4j)
    }
}
