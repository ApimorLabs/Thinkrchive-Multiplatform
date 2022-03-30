package work.racka.thinkrchive.v2.android.ui.main.screens.about

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getViewModel
import states.about.AboutSideEffect
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.*
import work.racka.thinkrchive.v2.common.features.about.viewmodel.AboutViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.aboutScreen(
    navController: NavHostController
) {
    composable(
        route = ThinkrchiveScreens.ThinkpadAboutScreen.name,
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
        val viewModel: AboutViewModel = getViewModel()
        val state by viewModel.host.state.collectAsState()
        val sideEffect = viewModel.host.sideEffect
            .collectAsState(initial = AboutSideEffect.NoSideEffect)
            .value

        // React to Side Effects
        when (sideEffect) {
            is AboutSideEffect.UpdateFound -> {
                ShowToastInCompose(message = sideEffect.toastMessage)
            }
            is AboutSideEffect.UpdateNotFound -> {
                ShowToastInCompose(message = sideEffect.toastMessage)
            }
            is AboutSideEffect.Update -> {
                // Call Update Logic Here
            }
            is AboutSideEffect.NoSideEffect -> {
                /* Do Nothing */
            }
        }

        AboutScreenUI(
            onCheckUpdates = {
                viewModel.host.update()
            },
            onBackButtonPressed = {
                navController.popBackStack()
            },
            appAbout = state.appAbout,
            hasUpdates = state.hasUpdates
        )
    }
}