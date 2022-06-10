buildscript {

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        // Leaving this plugin here so it can be automatically
        // updated by Android Studio
        classpath("com.android.tools.build:gradle:7.1.2")

        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.sqlDelight)
        classpath(BuildPlugins.composeDesktop)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
    }
}
