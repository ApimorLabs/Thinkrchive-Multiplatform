package work.racka.thinkrchive.v2.desktop.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component

class SettingsScreen(
    private val componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    private val logger = Logger.withTag("SettingsScreen")
    private val settings: AppSettings by inject(AppSettings::class.java)

    @Composable
    override fun render() {
        val state by settings.host.state.collectAsState()

        logger.d { "SettingsScreen Render call" }

        SettingsScreenUI(
            currentTheme = state.themeValue,
            currentSortOption = state.sortValue,
            onThemeOptionClicked = {
                settings.host.saveThemeSettings(it)
                //viewModel.saveThemeSetting(it)
            },
            onSortOptionClicked = {
                settings.host.saveSortSettings(it)
                //viewModel.saveSortOptionSetting(it)
            },
            onBackButtonPressed = onBackClicked
        )
    }

}

@Composable
fun SettingsScreen(
    settingsComponent: SettingsComponent
) {
    val logger = Logger.withTag("SettingsScreen")

    val state by settingsComponent.state.collectAsState()

    logger.d { "SettingsScreen Render call" }

    SettingsScreenUI(
        currentTheme = state.themeValue,
        currentSortOption = state.sortValue,
        onThemeOptionClicked = {
            settingsComponent.saveThemeSettings(it)
            //viewModel.saveThemeSetting(it)
        },
        onSortOptionClicked = {
            settingsComponent.saveSortSettings(it)
            //viewModel.saveSortOptionSetting(it)
        },
        onBackButtonPressed = {
            settingsComponent.backClicked()
        }
    )
}