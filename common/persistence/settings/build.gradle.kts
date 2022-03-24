plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm("desktop")

    sourceSets["commonMain"].dependencies {
        with(Dependencies.RusshWolf.MultiplatformSettings) {
            implementation(core)
            implementation(noArg)
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
        implementation(Dependencies.RusshWolf.MultiplatformSettings.test)
        implementation(Dependencies.Koin.test)
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
    }
}
