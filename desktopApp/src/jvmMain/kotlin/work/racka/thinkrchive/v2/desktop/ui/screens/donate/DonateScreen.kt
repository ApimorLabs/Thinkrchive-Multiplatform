package work.racka.thinkrchive.v2.desktop.ui.screens.donate

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.pop
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.features.donate.viewmodel.DonateViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Configuration

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun DonateScreen(
    router: Router<Configuration, Any>
) {

    val viewModel: DonateViewModel by inject(DonateViewModel::class.java)

    /*val state by viewModel.host.state.collectAsState()
    val sideEffect = viewModel.host.sideEffect
        .collectAsState(initial = DonateSideEffect.Nothing)
        .value

    when (sideEffect) {
        is DonateSideEffect.LaunchPurchaseFlow -> {
            // Will purchase
        }
        is DonateSideEffect.Error -> {
            //ShowToastInCompose(message = sideEffect.errorMsg)
        }
        is DonateSideEffect.ProductAuthorized -> {
            // Show the user what they bought
        }
        is DonateSideEffect.ShowPurchaseSuccessToast -> {
            //ShowToastInCompose(message = sideEffect.msg)
        }
        is DonateSideEffect.Nothing -> {}
    }*/

    DonateScreenUI(
        products = listOf(),
        onBackButtonPressed = router::pop,
        onProductClicked = {
            //viewModel.host.purchase(it)
        }
    )
}