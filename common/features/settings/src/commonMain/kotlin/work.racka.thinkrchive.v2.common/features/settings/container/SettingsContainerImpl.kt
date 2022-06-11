package work.racka.thinkrchive.v2.common.features.settings.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

internal class SettingsContainerImpl(
    private val settings: MultiplatformSettings,
    private val scope: CoroutineScope
) : SettingsContainer {

    private val _state: MutableStateFlow<ThinkpadSettingsState.State> =
        MutableStateFlow(ThinkpadSettingsState.DefaultState)

    override val state: StateFlow<ThinkpadSettingsState.State>
        get() = _state

    private val _sideEffect = Channel<ThinkpadSettingsSideEffect>(capacity = Channel.UNLIMITED)
    override val sideEffect: Flow<ThinkpadSettingsSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        readSettings()
    }

    private fun readSettings() {
        readThemeSettings()
        readSortSettings()
    }

    private fun readThemeSettings() {
        scope.launch {
            settings.themeFlow.collectLatest { themeValue ->
                _state.update { it.copy(themeValue = themeValue) }
            }
        }
    }

    private fun readSortSettings() {
        scope.launch {
            settings.sortFlow.collectLatest { sortValue ->
                _state.update { it.copy(sortValue = sortValue) }
            }
        }
    }

    override fun saveThemeSettings(themeValue: Int) {
        scope.launch {
            settings.saveThemeSettings(themeValue)
            _sideEffect.send(ThinkpadSettingsSideEffect.ApplyThemeOption(themeValue))
        }
    }

    override fun saveSortSettings(sortValue: Int) {
        scope.launch {
            settings.saveSortSettings(sortValue)
            _sideEffect.send(ThinkpadSettingsSideEffect.ApplySortOption(sortValue))
        }
    }
}