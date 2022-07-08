package work.racka.thinkrchive.v2.common.all_features.settings.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.common.mvvm.viewmodel.CommonViewModel
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

class SettingsViewModel(
    private val settings: MultiplatformSettings
) : CommonViewModel() {

    private val _state: MutableStateFlow<ThinkpadSettingsState.State> =
        MutableStateFlow(ThinkpadSettingsState.DefaultState)

    val state: StateFlow<ThinkpadSettingsState.State>
        get() = _state

    private val _sideEffect = Channel<ThinkpadSettingsSideEffect>(capacity = Channel.UNLIMITED)
    val sideEffect: Flow<ThinkpadSettingsSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        readSettings()
    }

    private fun readSettings() {
        readThemeSettings()
        readSortSettings()
    }

    private fun readThemeSettings() {
        vmScope.launch {
            settings.themeFlow.collectLatest { themeValue ->
                _state.update { it.copy(themeValue = themeValue) }
            }
        }
    }

    private fun readSortSettings() {
        vmScope.launch {
            settings.sortFlow.collectLatest { sortValue ->
                _state.update { it.copy(sortValue = sortValue) }
            }
        }
    }

    fun saveThemeSettings(themeValue: Int) {
        vmScope.launch {
            settings.saveThemeSettings(themeValue)
            _sideEffect.send(ThinkpadSettingsSideEffect.ApplyThemeOption(themeValue))
        }
    }

    fun saveSortSettings(sortValue: Int) {
        vmScope.launch {
            settings.saveSortSettings(sortValue)
            _sideEffect.send(ThinkpadSettingsSideEffect.ApplySortOption(sortValue))
        }
    }
}