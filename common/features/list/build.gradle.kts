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
        implementation(project(":common:model"))
        implementation(project(":common:features:settings"))
        implementation(project(":common:persistence:database"))
        implementation(project(":common:network"))

        implementation(Dependencies.Kotlin.Coroutines.core)

        implementation(Dependencies.OrbitMVI.core)

        with(Dependencies.Koin) {
            api(core)
            api(test)
        }
    }

    sourceSets["commonTest"].dependencies {
        implementation(Dependencies.Mockk.core)
        implementation(Dependencies.Mockk.commonMultiplatform)

        implementation(Dependencies.OrbitMVI.test)

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
