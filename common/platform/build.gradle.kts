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

    sourceSets["commonMain"].dependencies {

    }

    sourceSets["androidMain"].dependencies {

    }

    sourceSets["desktopMain"].dependencies {

    }

    sourceSets["commonTest"].dependencies {

    }

    sourceSets["androidTest"].dependencies {

    }
}