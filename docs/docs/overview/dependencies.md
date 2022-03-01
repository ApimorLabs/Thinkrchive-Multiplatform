---
sidebar_position: 3
---

# Dependencies and Plugins

This project contains first party platform dependencies & plugins and some third party dependencies
& plugins to provide functionality and easy development.

## Plugins

### Android Gradle plugin

```gradle
classpath("com.android.tools.build:gradle:7.1.2")
```

This adds Android Specific features to gradle for building Android apps

### Kotlin Gradle Plugin

```gradle
classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
```

This is adds Kotlin specific features to gradle for compiling Kotlin code

### SQLDelight

```gradle
classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}")
```

This adds support for [SQLDelight](https://github.com/cashapp/sqldelight) which is local SQL
database interface library that provides type-safe Kotlin APIs from your SQL statements.

### Compose Desktop Gradle Plugin

```gradle
classpath("org.jetbrains.compose:compose-gradle-plugin:${Versions.composeDesktop}")
```

This adds Compose for Desktop features to gradle for configuration and building Compose Desktop apps

## Dependencies

All dependencies used in this project are stored in `buildSrc` directory. They are all grouped based
on where they come from and their uses. You can easily look
at [`Dependencies.kt`](https://github.com/racka98/Thinkrchive-Multiplatform/blob/main/buildSrc/src/main/kotlin/Dependencies.kt)
inside `/buildSrc/src/main/kotlin/` to get their definitions.

### Android

This object contains all the Android only dependencies that are used or were used at some point in
this project. You can read through it to know all the dependencies that are provided in this project
for Android target. Every dependency has a comment stating what it's used for.

### Kotlin and Jetbrains

These objects provides the core Kotlin dependencies used which are:

1. `testCommon` - Dependency for testing Kotlin common code.

2. `kotlinJunit` - Kotlin JUnit runner for testing.

3. `serializationCore` & `kotlinJsonSerialization` - For Data class serialization and Json object
   serialization and deserialization.

### Koin

This object provides [Koin](https://insert-koin.io/) for Dependency Injection which is used
througout this project.

### Ktor

This object provides [Ktor Client](https://ktor.io/docs/client.html) which is used for Networking.
Ktor supports all Multiplatform targets.

### OrbitMVI

This object provides [Orbit MVI](https://orbit-mvi.org/) which is a Multiplatform MVI library that
is used to integrate the MVI architecture into our project.

### ArkIvanov - Decompose

This object provides the [Decompose](https://arkivanov.github.io/Decompose/) dependencies which are
used for Navigation in the desktop app. Decompose can also be used for Android since it's
Mutliplatform if you are ready to sacrifice some of the features provided by the official
Android-only Navigation library.

### RusshWolf - Multiplatform Settings

This object provides [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
dependencies for storing Settings and Configuration data. It is a Multiplatform library that saves
your from using the platform specific implementations and share more code.

### Squareup - SQLDelight

This object provides [SQLDelight](https://github.com/cashapp/sqldelight) dependencies for your
database needs across all Mutliplatform targets.

### Log

This object provides logging
dependencies.  [slf4j + logback](https://www.baeldung.com/kotlin/logging)
& [Kermit](https://github.com/touchlab/Kermit).
