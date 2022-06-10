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
        // Base Common Modules
        implementation(project(":common:billing"))
        implementation(project(":common:model"))
        implementation(project(":common:network"))
        implementation(project(":common:persistence:database"))
        implementation(project(":common:persistence:settings"))

        // Feature Modules
        implementation(project(":common:features:about"))
        implementation(project(":common:features:settings"))
        implementation(project(":common:features:list"))
        implementation(project(":common:features:details"))
        implementation(project(":common:features:donate"))

        implementation(Dependencies.Log.kermit)

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
