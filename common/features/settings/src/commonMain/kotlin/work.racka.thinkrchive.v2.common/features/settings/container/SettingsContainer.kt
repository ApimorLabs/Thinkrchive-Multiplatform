package work.racka.thinkrchive.v2.common.features.settings.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState

interface SettingsContainer {
    val state: StateFlow<ThinkpadSettingsState.State>
    val sideEffect: Flow<ThinkpadSettingsSideEffect>
    fun saveThemeSettings(themeValue: Int)
    fun saveSortSettings(sortValue: Int)
}