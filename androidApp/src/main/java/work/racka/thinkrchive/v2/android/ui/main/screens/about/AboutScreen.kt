package work.racka.thinkrchive.v2.android.ui.main.screens.about

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.AboutScreen(
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
        AboutScreenUI(
            onCheckUpdates = {
                // TODO: Check updates implementation
            },
            onBackButtonPressed = {
                navController.popBackStack()
            }
        )
    }
}