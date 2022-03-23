package work.racka.thinkrchive.v2.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.desktop.ui.navigation.App

fun main() = application {

    val logger = Logger.withTag("Main Function")
    logger.d { "Main Function Called" }
    KoinMain.initKoin()
    Window(onCloseRequest = ::exitApplication) {
        logger.d { "App Window Called" }
        App()
    }
}