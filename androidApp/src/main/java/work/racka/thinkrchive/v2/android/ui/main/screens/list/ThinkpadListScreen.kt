package work.racka.thinkrchive.v2.android.ui.main.screens.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import states.list.ThinkpadListSideEffect
import timber.log.Timber
import util.NetworkError
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel

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

        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Timber.d("thinkpadListScreen NavHost called")
        val host = viewModel.host
        val state by viewModel.host.state.collectAsState()
        val sideEffect = viewModel.host.sideEffect
            .collectAsState(initial = ThinkpadListSideEffect.NoSideEffect)
            .value

        // Work with the side effects here
        LaunchedEffect(sideEffect) {
            manageSideEffects(
                sideEffect = sideEffect,
                scaffoldState = scaffoldState,
                scope = scope,
                internetSettings = {},
                refresh = {
                    host.refreshThinkpadList()
                }
            )
        }

        ThinkpadListScreenUI(
            modifier = modifier,
            scaffoldState = scaffoldState,
            thinkpadList = state.thinkpadList,
            networkLoading = state.networkLoading,
            onRefresh = { host.refreshThinkpadList() },
            onSearch = { query ->
                host.getSortedThinkpadList(query)
            },
            onEntryClick = { thinkpad ->
                navController.navigate(
                    route = "$thinkpadDetailsScreen/${thinkpad.model}"
                )
            },
            networkError = "",
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

// Make sure it is called inside a LaunchedEffect
private suspend fun manageSideEffects(
    sideEffect: ThinkpadListSideEffect,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    internetSettings: () -> Unit = { },
    refresh: () -> Unit = { }
) {
    when (sideEffect) {
        is ThinkpadListSideEffect.ShowNetworkErrorSnackbar -> {
            when (sideEffect.networkError) {
                NetworkError.NoInternetError -> {
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = sideEffect.msg,
                            actionLabel = "CONNECT",
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            internetSettings()
                        }
                    }
                }
                NetworkError.StatusCodeError -> {
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = sideEffect.msg,
                            actionLabel = "RETRY",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            refresh()
                        }
                    }
                }
                NetworkError.SerializationError -> {
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = sideEffect.msg,
                            actionLabel = "RETRY",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            refresh()
                        }
                    }
                }
            }
        }
        is ThinkpadListSideEffect.NoSideEffect -> {

        }
    }
}