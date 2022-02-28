package work.racka.thinkrchive.v2.android.ui.main.screens.donate

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getViewModel
import work.racka.thinkrchive.v2.android.billing.qonversion.qonPurchase
import work.racka.thinkrchive.v2.android.ui.main.screenStates.DonateScreenState
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.ui.main.viewModel.DonateViewModel
import work.racka.thinkrchive.v2.android.ui.main.viewModel.QonversionViewModel
import work.racka.thinkrchive.v2.android.utils.scaleInEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleInPopEnterTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutExitTransition
import work.racka.thinkrchive.v2.android.utils.scaleOutPopExitTransition

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.DonateScreen(
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
        val viewModel: DonateViewModel = getViewModel()
        val donateScreenState by viewModel.uiState.collectAsState()
        val donateScreenData = donateScreenState as DonateScreenState.Donate

        val qonViewModel = QonversionViewModel()

        DonateScreenUI(
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