package work.racka.thinkrchive.v2.android.ui.main.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getViewModel
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.*
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadDetailsViewModel

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.ThinkpadDetailsScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val thinkpadDetailsScreen = ThinkrchiveScreens.ThinkpadDetailsScreen.name

    composable(
        route = "$thinkpadDetailsScreen/{thinkpad}",
        arguments = listOf(
            navArgument(name = "thinkpad") {
                type = NavType.StringType
            }
        ),
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
    ) { backStackEntry ->
        val thinkpadModel = backStackEntry.arguments?.getString("thinkpad")

        val viewModel: ThinkpadDetailsViewModel = getViewModel()
        viewModel.host.getThinkpad(thinkpadModel)

        val state by viewModel.host.state.collectAsState()
        val sideEffect = viewModel.host.sideEffect
            .collectAsState(initial = ThinkpadDetailsSideEffect.EmptySideEffect)
            .value

        when (sideEffect) {
            is ThinkpadDetailsSideEffect.OpenPsrefLink -> {
                val link = sideEffect.link
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link)
                    )
                }
                LocalContext.current.startActivity(intent)
            }
            is ThinkpadDetailsSideEffect.DisplayErrorMsg -> {
                ShowToastInCompose(message = sideEffect.message)
            }
            is ThinkpadDetailsSideEffect.EmptySideEffect -> {}
        }

        if (state is ThinkpadDetailsState.State) {
            val thinkpad =
                (state as ThinkpadDetailsState.State).thinkpad

            ThinkpadDetailsScreenUI(
                modifier = modifier,
                thinkpad = thinkpad,
                onBackButtonPressed = {
                    navController.popBackStack()
                },
                onExternalLinkClicked = {
                    viewModel.host.openPsrefLink()
                }
            )
        }
    }
}