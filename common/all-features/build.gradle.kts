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
        implementation(project(":common:mvvm-core"))

        implementation(project(":common:billing"))
        implementation(project(":common:persistence:settings"))
        implementation(Dependencies.Kotlin.Coroutines.core)
        with(Dependencies.Koin) {
            implementation(core)
            implementation(test)
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
        implementation(Dependencies.Revenuecat.android)
    }

    sourceSets["androidTest"].dependencies {
    }

    sourceSets["desktopMain"].dependencies {
    }

    sourceSets["desktopTest"].dependencies {

    }

    sourceSets["jsMain"].dependencies {
    }
}
