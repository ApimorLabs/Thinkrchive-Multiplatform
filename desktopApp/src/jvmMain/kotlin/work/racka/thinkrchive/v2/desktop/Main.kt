package work.racka.thinkrchive.v2.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.desktop.ui.navigation.App
import work.racka.thinkrchive.v2.desktop.ui.navigation.NavHostComponent

fun main() {
    val lifecycle = LifecycleRegistry()
    val root = NavHostComponent(DefaultComponentContext(lifecycle))

    application {
        val logger = Logger.withTag("Main Function")
        logger.d { "Main Function Called" }
        KoinMain.initKoin()

        Window(onCloseRequest = ::exitApplication) {
            logger.d { "App Window Called" }
            App(root)
        }
    }
}