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
        implementation(project(":common:billing"))

        implementation(Dependencies.Kotlin.Coroutines.core)

        implementation(Dependencies.OrbitMVI.core)

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }
    }

    sourceSets["androidMain"].dependencies {
        with(Dependencies.Android) {
            implementation(lifecycleRuntimeKtx)
            implementation(composeViewModel)
        }

        with(Dependencies.Koin) {
            implementation(android)
        }

        implementation(Dependencies.Revenuecat.android)
    }

    sourceSets["desktopMain"].dependencies {
    }
}
