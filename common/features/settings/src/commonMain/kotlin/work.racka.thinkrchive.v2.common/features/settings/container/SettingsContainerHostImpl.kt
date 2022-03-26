package work.racka.thinkrchive.v2.common.features.settings.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

internal class SettingsContainerHostImpl(
    private val settings: MultiplatformSettings,
    scope: CoroutineScope
) : SettingsContainerHost, ContainerHost<ThinkpadSettingsState.State, ThinkpadSettingsSideEffect> {

    override val container: Container<ThinkpadSettingsState.State, ThinkpadSettingsSideEffect> =
        scope.container(ThinkpadSettingsState.DefaultState) {
            readSettings()
        }

    override val state: StateFlow<ThinkpadSettingsState.State>
        get() = container.stateFlow

    override val sideEffect: Flow<ThinkpadSettingsSideEffect>
        get() = container.sideEffectFlow

    private fun readSettings() {
        readThemeSettings()
        readSortSettings()
    }

    private fun readThemeSettings() = intent {
        settings.themeFlow.collectLatest { themeValue ->
            reduce { state.copy(themeValue = themeValue) }
        }
    }

    private fun readSortSettings() = intent {
        settings.sortFlow.collectLatest { sortValue ->
            reduce { state.copy(sortValue = sortValue) }
        }
    }

    override fun saveThemeSettings(themeValue: Int) = intent {
        settings.saveThemeSettings(themeValue)
        postSideEffect(ThinkpadSettingsSideEffect.ApplyThemeOption(themeValue))
    }

    override fun saveSortSettings(sortValue: Int) = intent {
        settings.saveSortSettings(sortValue)
        postSideEffect(ThinkpadSettingsSideEffect.ApplySortOption(sortValue))
    }
}