package work.racka.thinkrchive.v2.desktop.ui.screens.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.pop
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.desktop.ui.navigation.Configuration

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    router: Router<Configuration, Any>
) {

    val settings: AppSettings by inject(AppSettings::class.java)
    val state by settings.host.state.collectAsState()

    SettingsScreenUI(
        currentTheme = state.themeValue,
        currentSortOption = state.sortValue,
        onThemeOptionClicked = {
            settings.host.saveThemeSettings(it)
            //viewModel.saveThemeSetting(it)
        },
        onSortOptionClicked = {
            settings.host.saveSortSettings(it)
            //viewModel.saveSortOptionSetting(it)
        },
        onBackButtonPressed = router::pop
    )
}