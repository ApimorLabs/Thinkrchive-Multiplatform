package work.racka.thinkrchive.v2.android.ui.main.screens.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import work.racka.common.mvvm.koin.compose.commonViewModel
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition
import work.racka.thinkrchive.v2.common.all_features.settings.viewmodel.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsScreen(
    navController: NavHostController
) {

    composable(
        route = ThinkrchiveScreens.ThinkpadSettingsScreen.name,
        enterTransition = {
            scaleInEnterTransition()
        },
        exitTransition = {
            scaleOutExitTransition()
        },
        popEnterTransition = {
            scaleInPopEnterTransition()
        },
        popExitTransition = {
            scaleOutPopExitTransition()
        }
    ) {

        val settings: SettingsViewModel by commonViewModel()
        val state by settings.state.collectAsState()

        SettingsScreenUI(
            currentTheme = state.themeValue,
            currentSortOption = state.sortValue,
            onThemeOptionClicked = {
                settings.saveThemeSettings(it)
                //viewModel.saveThemeSetting(it)
            },
            onSortOptionClicked = {
                settings.saveSortSettings(it)
                //viewModel.saveSortOptionSetting(it)
            },
            onBackButtonPressed = {
                navController.popBackStack()
            }
        )
    }
}