package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.RootComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.RootUI
import work.racka.thinkrchive.v2.desktop.ui.theme.Theme
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme
import kotlin.coroutines.EmptyCoroutineContext


@Composable
fun ComposeApp(root: RootComponent) {
    val settings: MultiplatformSettings by inject(MultiplatformSettings::class.java)
    val themeValue by settings.themeFlow.collectAsState(Theme.FOLLOW_SYSTEM.themeValue, EmptyCoroutineContext)

    val logger = Logger.withTag("App Main Entry")

    val flow = flowOf(1).collectAsState(1, Dispatchers.Main)
    ThinkRchiveTheme(
        theme = themeValue
    ) {
        logger.d { "Screen rendered" }
        Scaffold {
            RootUI(root)
        }
    }
}