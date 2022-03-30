package work.racka.thinkrchive.v2.android.ui.main.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.*
import work.racka.thinkrchive.v2.common.features.details.viewmodel.ThinkpadDetailsViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.thinkpadDetailsScreen(
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

        val viewModel: ThinkpadDetailsViewModel by viewModel {
            val thinkpadModel = backStackEntry.arguments?.getString("thinkpad")
            parametersOf(thinkpadModel)
        }

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

        when (state) {
            is ThinkpadDetailsState.State -> {
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
            is ThinkpadDetailsState.EmptyState -> {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}