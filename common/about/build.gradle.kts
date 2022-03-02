plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion

        buildConfigField(
            type = "String",
            name = "AppVersionName",
            value = "\"${AppConfig.versionName}\""
        )
        buildConfigField(
            type = "int",
            name = "AppVersionCode",
            value = "${AppConfig.versionCode}"
        )
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
        implementation(project(":common:model"))

        implementation(Dependencies.Kotlin.serializationCore)

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }
    }

    sourceSets["androidMain"].dependencies {
    }

    sourceSets["desktopMain"].dependencies {
    }
}
