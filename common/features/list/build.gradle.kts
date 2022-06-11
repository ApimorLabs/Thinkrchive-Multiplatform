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
        implementation(project(":common:data"))
        implementation(project(":common:model"))
        implementation(project(":common:persistence:settings"))
        implementation(Dependencies.Kotlin.Coroutines.core)
        with(Dependencies.Koin) {
            api(core)
            api(test)
        }
    }

    sourceSets["commonTest"].dependencies {
        implementation(Dependencies.Android.turbine)
        implementation(Dependencies.Koin.test)
        implementation(Dependencies.Kotlin.Coroutines.test)
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
    }

    sourceSets["androidMain"].dependencies {
        with(Dependencies.Android) {
            implementation(lifecycleRuntimeKtx)
            implementation(composeViewModel)
        }

        with(Dependencies.Koin) {
            implementation(android)
        }
    }

    sourceSets["androidTest"].dependencies {
        implementation(Dependencies.Android.junit)
    }

    sourceSets["desktopMain"].dependencies {
    }

    sourceSets["desktopTest"].dependencies {

    }
}
