package work.racka.thinkrchive.v2.android.ui.main.screens.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getViewModel
import states.list.ThinkpadListSideEffect
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition
import work.racka.thinkrchive.v2.common.features.thinkpad_list.viewmodel.ThinkpadListViewModel

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.ThinkpadListScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val thinkpadDetailsScreen = ThinkrchiveScreens.ThinkpadDetailsScreen.name

    // Main List Screen
    composable(
        route = ThinkrchiveScreens.ThinkpadListScreen.name,

        // Transition animations
        enterTransition = {
            scaleInEnterTransition()
        },
        exitTransition = {
            scaleOutExitTransition()
        },
        // popEnter and popExit default to enterTransition & exitTransition respectively
        popEnterTransition = {
            scaleInPopEnterTransition()
        },
        popExitTransition = {
            scaleOutPopExitTransition()
        }
    ) {
        val viewModel: ThinkpadListViewModel = getViewModel()

        Timber.d("thinkpadListScreen NavHost called")
        val host = viewModel.host
        val state by viewModel.host.state.collectAsState()
        val sideEffect = viewModel.host.sideEffect
            .collectAsState(initial = ThinkpadListSideEffect.Network())
            .value as ThinkpadListSideEffect.Network

        ThinkpadListScreenUI(
            modifier = modifier,
            thinkpadList = state.thinkpadList,
            networkLoading = sideEffect.isLoading,
            onSearch = { query ->
                host.getSortedThinkpadList(query)
            },
            onEntryClick = { thinkpad ->
                navController.navigate(
                    route = "$thinkpadDetailsScreen/${thinkpad.model}"
                )
            },
            networkError = sideEffect.errorMsg,
            currentSortOption = state.sortOption,
            onSortOptionClicked = { sort ->
                host.sortSelected(sort)
            },
            onSettingsClicked = {
                navController.navigate(
                    route = ThinkrchiveScreens.ThinkpadSettingsScreen.name
                )
            },
            onAboutClicked = {
                navController.navigate(
                    route = ThinkrchiveScreens.ThinkpadAboutScreen.name
                )
            },
            onDonateClicked = {
                navController.navigate(
                    route = ThinkrchiveScreens.DonationScreen.name
                )
            }
        )
    }
}