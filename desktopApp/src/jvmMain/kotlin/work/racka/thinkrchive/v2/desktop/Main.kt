package work.racka.thinkrchive.v2.desktop

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.desktop.ui.navigation.ComposeApp
import work.racka.thinkrchive.v2.desktop.ui.navigation.NavHostComponent

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val root = NavHostComponent(DefaultComponentContext(lifecycle))

    application {
        val logger = Logger.withTag("Main Function")
        logger.d { "Main Function Called" }
        KoinMain.initKoin()

        val windowState = rememberWindowState(size = DpSize(1000.dp, 600.dp))
        LifecycleController(lifecycleRegistry = lifecycle, windowState = windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState
        ) {
            logger.d { "App Window Called" }
            ComposeApp(root)
        }
    }
}