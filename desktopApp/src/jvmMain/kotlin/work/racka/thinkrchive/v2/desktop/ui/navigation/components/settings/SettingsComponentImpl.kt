package work.racka.thinkrchive.v2.desktop.ui.navigation.components.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import states.settings.ThinkpadSettingsState
import work.racka.common.mvvm.koin.decompose.commonViewModel
import work.racka.thinkrchive.v2.common.all_features.settings.viewmodel.SettingsViewModel

class SettingsComponentImpl(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : SettingsComponent, ComponentContext by componentContext {

    private val settings: SettingsViewModel by commonViewModel()

    override val state: StateFlow<ThinkpadSettingsState.State>
        get() = settings.state

    override fun backClicked() {
        onBackClicked()
    }

    override fun saveThemeSettings(themeValue: Int) {
        settings.saveThemeSettings(themeValue)
    }

    override fun saveSortSettings(sortValue: Int) {
        settings.saveSortSettings(sortValue)
    }

    init {
        lifecycle.doOnDestroy {
            println("Settings Destroyed")
            println("Is scope Active: ${settings.vmScope.coroutineContext.isActive}")
        }
    }
}