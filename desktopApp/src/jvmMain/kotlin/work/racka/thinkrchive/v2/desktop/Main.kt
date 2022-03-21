package work.racka.thinkrchive.v2.desktop

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.desktop.ui.navigation.App

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun main() = application {

    KoinMain.initKoin()
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}