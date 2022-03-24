import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
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

    sourceSets["commonMain"].dependencies {
        implementation(project(":common:network"))
        implementation(project(":common:model"))

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

    sourceSets["commonTest"].dependencies {
        implementation(Dependencies.Koin.test)
        implementation(Dependencies.Kotlin.Coroutines.test)
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
    }

    sourceSets["androidMain"].dependencies {
        implementation(Dependencies.Squareup.SQLDelight.androidDriver)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Dependencies.Squareup.SQLDelight.sqliteDriver)
        implementation(Dependencies.Android.junit)
    }

    sourceSets["desktopMain"].dependencies {
        implementation(Dependencies.Squareup.SQLDelight.sqliteDriver)
        implementation(Dependencies.Log.slf4j)
    }

    sourceSets["desktopTest"].dependencies {

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
