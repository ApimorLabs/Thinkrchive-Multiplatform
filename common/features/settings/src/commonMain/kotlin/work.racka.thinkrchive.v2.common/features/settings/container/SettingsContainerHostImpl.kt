package work.racka.thinkrchive.v2.common.features.settings.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.settings.repository.SettingsRepository

internal class SettingsContainerHostImpl(
    private val settings: SettingsRepository,
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
        val themeValue = settings.readThemeSettings()
        postSideEffect(ThinkpadSettingsSideEffect.ApplyThemeOption(themeValue))
        reduce { state.copy(themeValue = themeValue) }
    }

    private fun readSortSettings() = intent {
        val sortValue = settings.readSortSettings()
        postSideEffect(ThinkpadSettingsSideEffect.ApplySortOption(sortValue))
        reduce { state.copy(sortValue = sortValue) }
    }

    override fun saveThemeSettings(themeValue: Int) = intent {
        settings.saveThemeSettings(themeValue)
        readThemeSettings()
    }

    override fun saveSortSettings(sortValue: Int) = intent {
        settings.saveSortSettings(sortValue)
        readSortSettings()
    }
}