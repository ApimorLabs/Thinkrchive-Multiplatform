import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = "work.racka.thinkrchive.v2"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(
            project.rootProject.file("local.properties")
                .reader()
        )
        buildConfigField(
            "String",
            "qonversion_key",
            properties.getProperty("qonversion_key")
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/licenses/ASM"
            )
            // Fixes conflicts caused by ByteBuddy library used in
            // coroutines-debug and mockito
            pickFirsts += mutableSetOf(
                "win32-x86-64/attach_hotspot_windows.dll",
                "win32-x86/attach_hotspot_windows.dll"
            )
        }
    }
}

dependencies {
    implementation(project(":common:model"))
    implementation(project(":common:integration"))

    // Core Functionality
    with(Dependencies.Android) {
        implementation(coreKtx)
        implementation(appCompat)
        implementation(material)
        implementation(lifecycleRuntimeKtx)
    }

    with(Dependencies.OrbitMVI) {
        implementation(core)
    }

    // Testing
    with(Dependencies.Android) {
        testImplementation(junit)
        testImplementation(testArchCore)
        testImplementation(junitTest)
        testImplementation(testExtJUnitKtx)
        testImplementation(turbine)
        testImplementation(coroutineTest)

        androidTestImplementation(junitTest)
        androidTestImplementation(espressoCore)
        androidTestImplementation(testCoreKtx)
        androidTestImplementation(testArchCore)
        androidTestImplementation(turbine)
    }

    // Testing Compose
    with(Dependencies.Android) {
        androidTestImplementation(junitCompose)
        debugImplementation(composeTooling)
    }

    // Compose
    with(Dependencies.Android) {
        implementation(composeUi)
        implementation(composeAnimation)
        implementation(composeMaterial)
        implementation(composePreview)
        implementation(composeActivity)
        implementation(composeViewModel)
        implementation(composeNavigation)
        implementation(composeMaterialIconsCore)
        implementation(composeMaterialIconsExtended)
        implementation(composeFoundation)
        implementation(composeFoundationLayout)
        implementation(composeConstraintLayout)
        implementation(composeMaterial3)
    }

    // Timber
    implementation(Dependencies.Android.timber)

    // Dependency Injection
    with(Dependencies.Koin) {
        implementation(android)
        implementation(compose)
    }

    // Coil Image loader
    implementation(Dependencies.Android.coilImage)

    // Accompanist
    with(Dependencies.Android) {
        implementation(accompanistInsets)
        implementation(accompanistNavigationAnimations)
    }

    // Splash Screen
    implementation(Dependencies.Android.splashScreenCore)

    // Billing
    with(Dependencies.Android) {
        implementation(googleBilling)
        implementation(qonversion)
    }

}