package work.racka.thinkrchive.v2.android.ui.main.screens.donate

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.revenuecat.purchases.Package
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel
import states.donate.DonateSideEffect
import timber.log.Timber
import util.Resource
import work.racka.thinkrchive.v2.android.billing.revenuecat.Purchase
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.utils.*
import work.racka.thinkrchive.v2.common.features.donate.viewmodel.DonateViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.donateScreen(
    navController: NavHostController
) {
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

        val viewModel: DonateViewModel by viewModel()
        val state by viewModel.host.state.collectAsState()
        val sideEffect = viewModel.host.sideEffect
            .collectAsState(initial = DonateSideEffect.Nothing)
            .value

        val purchase by lazy { Purchase() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(purchase.result) {
            scope.launch {
                Timber.d("Scope launched")
                purchase.result.collectLatest {
                    Timber.d("Collecting State")
                    when (it) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            viewModel.host.processPurchase(true)
                        }
                        is Resource.Error -> {
                            viewModel.host.processPurchase(false, it.message!!)
                        }
                    }
                }
            }
        }

        when (sideEffect) {
            is DonateSideEffect.LaunchPurchaseFlow -> {
                purchase.initiatePurchase(currentActivity, sideEffect.item as Package)
                viewModel.host.detachSideEffect()
            }
            is DonateSideEffect.Error -> {
                ShowToastInCompose(message = sideEffect.errorMsg)
            }
            is DonateSideEffect.ProductAuthorized -> {
                // Show the user what they bought
            }
            is DonateSideEffect.ShowPurchaseSuccessToast -> {
                ShowToastInCompose(message = sideEffect.msg)
            }
            is DonateSideEffect.Nothing -> {}
        }

        DonateScreenUI(
            products = state.product,
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onProductClicked = {
                viewModel.host.purchase(it)
            }
        )
    }
}