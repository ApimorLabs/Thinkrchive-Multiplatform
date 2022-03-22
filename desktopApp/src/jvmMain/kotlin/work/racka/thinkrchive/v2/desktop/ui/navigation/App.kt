package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.koin.java.KoinJavaComponent.inject
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme


@Composable
fun App() {
    val settings: AppSettings by inject(AppSettings::class.java)
    val state: ThinkpadSettingsState.State by settings.host.state.collectAsState()
    val lifecycle = LifecycleRegistry()
    val root = NavHostComponent(DefaultComponentContext(lifecycle))

    val logger = Logger.withTag("App Main Entry")

    ThinkRchiveTheme(
        theme = state.themeValue
    ) {
        Scaffold {
            logger.d { "Screen rendered" }
            root.render()
        }
    }
}