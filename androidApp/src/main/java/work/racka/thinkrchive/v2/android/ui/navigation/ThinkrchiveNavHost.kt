package work.racka.thinkrchive.v2.android.ui.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import timber.log.Timber
import work.racka.thinkrchive.v2.android.billing.qonversion.qonPurchase
import work.racka.thinkrchive.v2.android.ui.main.screenStates.DonateScreenState
import work.racka.thinkrchive.v2.android.ui.main.screenStates.ThinkpadDetailsScreenState
import work.racka.thinkrchive.v2.android.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.v2.android.ui.main.screenStates.ThinkpadSettingsScreenState
import work.racka.thinkrchive.v2.android.ui.main.screens.*
import work.racka.thinkrchive.v2.android.ui.main.viewModel.*
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition


@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = ThinkrchiveScreens.ThinkpadListScreen.name,
        route = "MainNavHost"
    ) {
        Timber.d("thinkpadNavHost called")
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
            val thinkpadListState by viewModel.uiState.collectAsState()
            val thinkpadListScreenData =
                thinkpadListState as ThinkpadListScreenState.ThinkpadListScreen

            Timber.d("thinkpadListScreen NavHost called")

            ThinkpadListScreen(
                modifier = modifier,
                thinkpadList = thinkpadListScreenData.thinkpadList,
                networkLoading = thinkpadListScreenData.networkLoading,
                onSearch = { query ->
                    viewModel
                        .getNewThinkpadListFromDatabase(query)
                },
                onEntryClick = { thinkpad ->
                    navController.navigate(
                        route = "$thinkpadDetailsScreen/${thinkpad.model}"
                    )
                },
                networkError = thinkpadListScreenData.networkError,
                currentSortOption = thinkpadListScreenData.sortOption,
                onSortOptionClicked = { sort ->
                    viewModel.sortSelected(sort)
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

        // Details Screen
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

            val thinkpadDetailsViewModel: ThinkpadDetailsViewModel by viewModel()
            thinkpadDetailsViewModel.getThinkpad(thinkpadModel)

            val thinkpadDetail = thinkpadDetailsViewModel.uiState.collectAsState()
            val context = LocalContext.current

            if (thinkpadDetail.value is ThinkpadDetailsScreenState.ThinkpadDetail) {
                val thinkpad =
                    (thinkpadDetail.value as ThinkpadDetailsScreenState.ThinkpadDetail).thinkpad
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(thinkpad.psrefLink)
                    )
                }
                ThinkpadDetailsScreen(
                    modifier = modifier,
                    thinkpad = thinkpad,
                    onBackButtonPressed = {
                        navController.popBackStack()
                    },
                    onExternalLinkClicked = {
                        context.startActivity(intent)
                    }
                )
            }
        }


        // Settings Screen
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
            val viewModel: ThinkpadSettingsViewModel = getViewModel()
            val settingsScreenState by viewModel.uiState.collectAsState()
            if (settingsScreenState is ThinkpadSettingsScreenState.ThinkpadSettings) {
                val settingsScreenData =
                    settingsScreenState as ThinkpadSettingsScreenState.ThinkpadSettings

                ThinkpadSettingsScreen(
                    currentTheme = settingsScreenData.themeOption,
                    currentSortOption = settingsScreenData.sortOption,
                    onThemeOptionClicked = {
                        viewModel.saveThemeSetting(it)
                    },
                    onSortOptionClicked = {
                        viewModel.saveSortOptionSetting(it)
                    },
                    onBackButtonPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // About Screen
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
            ThinkpadAboutScreen(
                onCheckUpdates = {
                    // TODO: Check updates implementation
                },
                onBackButtonPressed = {
                    navController.popBackStack()
                }
            )
        }

        // Donate Screen
        composable(
            route = ThinkrchiveScreens.DonationScreen.name,
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
            val currentActivity = LocalContext.current as Activity
            val viewModel: DonateViewModel = getViewModel()
            val donateScreenState by viewModel.uiState.collectAsState()
            val donateScreenData = donateScreenState as DonateScreenState.Donate

            val qonViewModel = QonversionViewModel()

            DonateScreen(
                skuList = donateScreenData.skuDetailsList,
                onDonateItemClicked = {
                    viewModel.launchPurchaseScreen(currentActivity, it)
                },
                onBackButtonPressed = {
                    navController.popBackStack()
                },
                qonViewModel = qonViewModel,
                onOfferingClicked = {
                    qonPurchase(currentActivity, it, qonViewModel)
                }
            )
        }
    }

}
