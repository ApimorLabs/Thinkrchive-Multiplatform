package work.racka.thinkrchive.v2.desktop.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.touchlab.kermit.Logger
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.settings.SettingsComponent

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