---
sidebar_position: 1
---

# Project Details

Thinkrchive - Multiplatform is an Android & Desktop app that shows specifications and details of
various Lenovo Thinkpad models. This project was made to try out Jetpack Compose and Kotlin
Multiplatform so that I can see how I could adopt it to my own personal apps.

This app will also be documented heavily so that it can help anyone else in the future that is
trying to get into Kotlin Multiplatform to support Android and Desktop
via [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/)

> This repo is a Mutliplatform version of the initial [Thinkrchive](https://github.com/racka98/ThinkRchive) which was Android only

| Light | Dark |
|-------|------|
|![Screenshot](https://i.imgur.com/DX6DhQP.png)|![Screenshot](https://i.imgur.com/XAm5ld0.png)

## 🤳 Screenshots

|![Screenshot](https://i.imgur.com/DX6DhQP.png)|![Screenshot](https://i.imgur.com/XAm5ld0.png)|![Screenshot](https://i.imgur.com/Q8muSdP.png)|
|-------|------|------|
|![Screenshot](https://i.imgur.com/jg1VClv.png)|![Screenshot](https://i.imgur.com/llz2peN.png)|![Screenshot](https://i.imgur.com/RRsKGOG.png)|
|![Screenshot](https://i.imgur.com/2yVTC6l.png)|![Screenshot](https://i.imgur.com/6MDKZYj.png)|![Screenshot](https://i.imgur.com/4aYBsCi.png)|

- [More Coming Soon](https://)

## 🏋 Tools

- Java 11 or above
- Android Studio Bumblebee | 2021.1+

## 🏗️️ Built with

***Compared to [Thinkrchive](https://github.com/racka98/ThinkRchive) which was Android only***

| Component       | Thinkrchive                   | Thinkrchive-Multiplaform                |
|----------------|------------------------------|-----------------------------------------|
| 🎭 User Interface    | [Jetpack Compose](https://developer.android.com/jetpack/compose)   | [Compose Mulitplatform](https://www.jetbrains.com/lp/compose-mpp/)  |
| 🏗 Architecture    | [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)  |  [MVI](https://github.com/MostafaBorjali/MVI-Architecture/wiki/MVI-Architecture-Wiki) - [Orbit MVI](https://orbit-mvi.org/) |
| 🧠 Backend    | [Thinkrchive Ktor Server](https://github.com/racka98/Thinkrchive-Server) | [Thinkrchive Ktor Server](https://github.com/racka98/Thinkrchive-Server)  |
| 💉 DI                | [Hilt](https://dagger.dev/hilt/)  | [Koin](https://insert-koin.io/)  |
| 🛣️ Navigation        | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)   | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation), [Decompose](https://arkivanov.github.io/Decompose/)  |
| 🌊 Async            | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow + StateFlow + SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow + StateFlow + SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) |
| 🌐 Networking        | [Ktor Client](https://ktor.io/docs/client.html) | [Ktor Client](https://ktor.io/docs/client.html)  |
| 📄 JSON            | [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) | [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)  |
| 💾 Persistance     | [Room](https://developer.android.com/training/data-storage/room) + [Preference DataStore](https://developer.android.com/topic/libraries/architecture/datastore)   | [SQLDelight](https://cashapp.github.io/sqldelight/), [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) |
| ⌨️ Logging            | [Timber](https://github.com/JakeWharton/timber) | [Timber](https://github.com/JakeWharton/timber) - Android, [slf4j + logback](https://www.baeldung.com/kotlin/logging), [Kermit](https://github.com/touchlab/Kermit) |
| 📸 Image Loading      | [Coil](https://coil-kt.github.io/coil/) | [Coil](https://coil-kt.github.io/coil/) |
| 🔧 Supplimentary   | [Accompanist](https://github.com/google/accompanist)  | [Accompanist](https://github.com/google/accompanist) |
| 🧪 Testing            | [Mockito](https://site.mockito.org/) + [JUnit](https://github.com/junit-team/junit5) + [Robolectric](https://github.com/robolectric/robolectric)   | Not Setup Yet  |

## 🧐 Fun Facts

:::tip Fun little facts

- ThinkRchive originally
  used [a google sheet](https://docs.google.com/spreadsheets/d/1cFrYzzAP7i3bzSLKuBMykz3ZNUbf-YPTqRSEAwINy_E/edit?usp=sharing)
  as the backend via [Retrosheet](https://github.com/theapache64/retrosheet)

- ThinkRchive now
  uses [a custom Ktor powered server client](https://github.com/racka98/Thinkrchive-Server) as the
  backend. The backend is hosted on [Heroku](https://www.heroku.com)

- I mocked the designs for this app on Figma and the finished app looks better than my initial
  designs

:::

## ✅ TODO

- Add more Tests (Unit Tests, UI Tests, Integration Tests)
- Add more features
- Support for more platforms

## 🙇 Credits

- Special thanks to [@theapache64](https://github.com/theapache64)
  for [readgen](https://github.com/theapache64/readgen)
- All the maintainers of the 3rd party libraries used in this project.
- Thanks to all amazing people at Twitter for inspiring me to continue the development of this
  project.

## 🤝 Contributing

- See [CONTRIBUTING](https://github.com/racka98/Thinkrchive-Multiplatform/blob/main/CONTRIBUTING.md)

## ❤ Show your support

Give a ⭐️ if this project helped you!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/U6U44Y0MQ)

## 📝 License

- [Full License](https://github.com/racka98/Thinkrchive-Multiplatform/blob/main/LICENSE)

```md
    ThinkRhcive - An app showing all details for various Lenovo Thinkpad models.
    Copyright (C) 2021  racka98

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
```

_**Made With ❤ From Tanzania 🇹🇿**_
