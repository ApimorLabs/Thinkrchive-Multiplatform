package work.racka.thinkrchive.v2.common.integration.containers.settings

import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.settings.ThinkpadSettingsSideEffect
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.settings.SettingsRepository

class ThinkpadSettingsContainerHost(
    private val settings: SettingsRepository,
    scope: CoroutineScope
) : ContainerHost<ThinkpadSettingsState.State, ThinkpadSettingsSideEffect> {

    override val container: Container<ThinkpadSettingsState.State, ThinkpadSettingsSideEffect> =
        scope.container(ThinkpadSettingsState.DefaultState) {
            readSettings()
        }

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

    fun saveThemeSettings(themeValue: Int) = intent {
        settings.saveThemeSettings(themeValue)
        readThemeSettings()
    }

    fun saveSortSettings(sortValue: Int) = intent {
        settings.saveSortSettings(sortValue)
        readSortSettings()
    }
}