package work.racka.thinkrchive.v2.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.desktop.ui.navigation.App

fun main() = application {

    KoinMain.initKoin()
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}