package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import org.koin.java.KoinJavaComponent.inject
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme


@Composable
fun App(root: RootComponent) {
    val settings: AppSettings by inject(AppSettings::class.java)
    val state: ThinkpadSettingsState.State by settings.host.state.collectAsState()

    val logger = Logger.withTag("App Main Entry")

    ThinkRchiveTheme(
        theme = state.themeValue
    ) {
        logger.d { "Screen rendered" }
        Scaffold {
            RootUI(root)
        }
    }
}