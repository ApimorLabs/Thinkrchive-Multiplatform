package work.racka.thinkrchive.v2.desktop.ui.navigation.components.settings

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject
import states.settings.ThinkpadSettingsState
import work.racka.thinkrchive.v2.common.features.settings.AppSettings

class SettingsComponentImpl(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : SettingsComponent, ComponentContext by componentContext {
    private val settings: AppSettings by inject(AppSettings::class.java)

    override val state: StateFlow<ThinkpadSettingsState.State>
        get() = settings.host.state

    override fun backClicked() {
        onBackClicked()
    }

    override fun saveThemeSettings(themeValue: Int) {
        settings.host.saveThemeSettings(themeValue)
    }

    override fun saveSortSettings(sortValue: Int) {
        settings.host.saveSortSettings(sortValue)
    }
}