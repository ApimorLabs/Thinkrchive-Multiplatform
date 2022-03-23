package work.racka.thinkrchive.v2.desktop.ui.screens.settings

import kotlinx.coroutines.flow.StateFlow
import states.settings.ThinkpadSettingsState

interface SettingsComponent {
    val state: StateFlow<ThinkpadSettingsState.State>
    fun backClicked()
    fun saveThemeSettings(themeValue: Int)
    fun saveSortSettings(sortValue: Int)
}