# 🏛 ThinkRchive

| Light | Dark |
|-------|------|
|![](https://i.imgur.com/DX6DhQP.png)|![](https://i.imgur.com/XAm5ld0.png)

<a href="https://twitter.com/rackadev" target="_blank">
<img alt="Twitter: rackadev" src="https://img.shields.io/twitter/follow/rackadev.svg?style=social" />
</a>

> An app showing all details for various Lenovo Thinkpad models. Made to try out Jepack Compose for Android.
> This repo is a Mutliplatform version of the initial [Thinkrchive](https://github.com/racka98/ThinkRchive) which was Android-centric

### ✨ Documentation

- [Thinkrchive Multiplatform Docs](https://thinkrchive.github.io/Thinkrchive-Multiplatform/)

### 🤳 Screenshots

|![](https://i.imgur.com/DX6DhQP.png)|![](https://i.imgur.com/XAm5ld0.png)|![](https://i.imgur.com/Q8muSdP.png)|
|-------|------|------|
|![](https://i.imgur.com/jg1VClv.png)|![](https://i.imgur.com/llz2peN.png)|![](https://i.imgur.com/RRsKGOG.png)|
|![](https://i.imgur.com/2yVTC6l.png)|![](https://i.imgur.com/6MDKZYj.png)|![](https://i.imgur.com/4aYBsCi.png)|

- [More Coming Soon]()

## 💻 Install

| Platform | Download | Status |
|----------|----------|--------|
| Android  |[![Download Button](https://img.shields.io/static/v1?label=Thinkrchive&message=v1.0.0-alpha03&color=blue)](https://github.com/racka98/Thinkrchive-Multiplatform/raw/release/androidApp/release/androidApp-release.apk)| 🧪 Beta |
| Desktop - Windows  |[![Download Button](https://img.shields.io/static/v1?label=In-Progress&message=v0.0.0-experimental00&color=green)]()| 🧪 Experimental |
| Desktop - macOS  |[![Download Button](https://img.shields.io/static/v1?label=In-Progress&message=v0.0.0-experimental00&color=yellow)]()| 🧪 Experimental |
| Desktop - Linux  |[![Download Button](https://img.shields.io/static/v1?label=In-Progress&message=v0.0.0-experimental00&color=purple)]()| 🧪 Experimental |

> ℹ️ Compose Debug apks are sometimes laggy as they contain a lot of debug code.
> 
> ℹ️ The apk above is a signed release version and should perform as expected.

## 🏋 Dependency

- Java 11 or above
- Android Studio Bumblebee | 2021.1+

## 🏗️️ Built with

### Compared to [Thinkrchive](https://github.com/racka98/ThinkRchive) which was Android-centric

| Component       | Thinkrchive                   | Thinkrchive-Multiplaform                |
|----------------	|------------------------------	|-----------------------------------------|
| 🎭  User Interface    | [Jetpack Compose](https://developer.android.com/jetpack/compose)   | [Compose Mulitplatform](https://www.jetbrains.com/lp/compose-mpp/)  |
| 🏗  Architecture    | [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)  |  [MVI](https://github.com/MostafaBorjali/MVI-Architecture/wiki/MVI-Architecture-Wiki) - Not Completed Yet |
| 🧠  Backend    | [Thinkrchive Ktor Server](https://github.com/racka98/Thinkrchive-Server) | [Thinkrchive Ktor Server](https://github.com/racka98/Thinkrchive-Server)  |
| 💉  DI                | [Hilt](https://dagger.dev/hilt/)  | [Koin](https://insert-koin.io/)  |
| 🛣️  Navigation        | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)   | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation), [Decompose](https://arkivanov.github.io/Decompose/)  |
| 🌊  Async            | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow + StateFlow + SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow + StateFlow + SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) |
| 🌐  Networking        | [Ktor Client](https://ktor.io/docs/client.html) | [Ktor Client](https://ktor.io/docs/client.html)  |
| 📄  JSON            | [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) | [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)  |
| 💾  Persistance     | [Room](https://developer.android.com/training/data-storage/room) + [Preference DataStore](https://developer.android.com/topic/libraries/architecture/datastore)   | [SQLDelight](https://cashapp.github.io/sqldelight/), [Preference DataStore](https://developer.android.com/topic/libraries/architecture/datastore), [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) - Future Plan  |
| ⌨️  Logging            | [Timber](https://github.com/JakeWharton/timber) | [Timber](https://github.com/JakeWharton/timber) - Android, [slf4j + logback](https://www.baeldung.com/kotlin/logging), [Kermit](https://github.com/touchlab/Kermit) |
| 📸  Image Loading      | [Coil](https://coil-kt.github.io/coil/) | [Coil](https://coil-kt.github.io/coil/) |
| 🔧  Supplimentary   | [Accompanist](https://github.com/google/accompanist)  | [Accompanist](https://github.com/google/accompanist) |
| 🧪  Testing            | [Mockito](https://site.mockito.org/) + [JUnit](https://github.com/junit-team/junit5) + [Robolectric](https://github.com/robolectric/robolectric)   | Not Setup Yet  |

## 🧐 Fun Facts

- ThinkRchive
  originally used [a google sheet](https://docs.google.com/spreadsheets/d/1cFrYzzAP7i3bzSLKuBMykz3ZNUbf-YPTqRSEAwINy_E/edit?usp=sharing)
  as the backend via [Retrosheet](https://github.com/theapache64/retrosheet)
  
- ThinkRchive now uses [a custom Ktor powered server client](https://github.com/racka98/Thinkrchive-Server) as the backend. The backend is hosted on [Heroku](https://www.heroku.com)

- I mocked the designs for this app on Figma and the finished app looks better than my initial designs

## ✅ TODO

- Add more Tests (Unit Tests, UI Tests, Integration Tests)
- Add more features
- Support for more platform
- Fully switch to MVI

## 🙇 Credits

- Special thanks to [@theapache64](https://github.com/theapache64) for [readgen](https://github.com/theapache64/readgen)
- Thanks to all amazing people at Twitter for inspiring me to continue the development of this project.

## 🤝 Contributing

- See [CONTRIBUTING](/CONTRIBUTING.md)

## ❤ Show your support

Give a ⭐️ if this project helped you!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/U6U44Y0MQ)

## 📝 License

- [Full License](/LICENSE)

```
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

_This README was generated by [readgen](https://github.com/theapache64/readgen)_ ❤
