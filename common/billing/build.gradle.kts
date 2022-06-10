plugins {
    kotlin("multiplatform")
    id("com.android.library")
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

    js(IR) {
        binaries.executable()
        useCommonJs()
        browser()
    }

    sourceSets["commonMain"].dependencies {
        implementation(project(":common:model"))

        implementation(Dependencies.Kotlin.serializationCore)

        implementation(Dependencies.Kotlin.Coroutines.core)

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }
    }

    sourceSets["androidMain"].dependencies {
        implementation(Dependencies.Revenuecat.android)
    }

    sourceSets["desktopMain"].dependencies {
    }
}
