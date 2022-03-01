---
sidebar_position: 2
---

# Setup

Opening this project on your machine is straightforward if you have ever worked with Intellij or
Android Studio.

Feel free to skip this if you already know how to open projects on Android Studio

## Prerequisites

- You need to have installed Android Studio Bumblee 2021.1+

:::danger Android Studio Arctic Fox or ealier will fail to build the project due to it not
supporting newer Gradle versions after 7.1 used in this project. You can manually change the Gradle
version on older Android Studio to be able to build it but you may encounter other errors in the
future.
:::

- Java 11 or later is needed. This will included in Android Studio unless you specifically changed
  the JDK to one you downloaded yourself.

## Opening the Project

1. Download this project from [github](https://github.com/racka98/Thinkrchive-Multiplatform) or
   clone it

```bash
git clone https://github.com/racka98/Thinkrchive-Multiplatform.git
```

2. In Android Studio choose Open and locate the project on your computer

3. Open the project and wait for Android Studio to download all the dependencies and configure your
   app

## Running the App

### Android App

:::tip Run Choose your prefered emulator or device from Android Studio and click the Run button.
:::

### Desktop App

:::tip In the Terminal

```bash
./gradlew :desktop-App
```

:::

## Errors

### Configuration Cache

You may encounter Configuration Cache errors or warnings when you try to rebuild the app after
changing a couple of things. This is because this project
enables [gradle configuration cache](https://docs.gradle.org/current/userguide/configuration_cache.html)
.

:::tip Solution Just delete the `/.gradle/configration-cache` folder and rerun the build
:::
