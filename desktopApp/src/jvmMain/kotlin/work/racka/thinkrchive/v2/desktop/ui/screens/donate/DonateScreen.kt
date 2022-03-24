package work.racka.thinkrchive.v2.desktop.ui.screens.donate

import androidx.compose.runtime.Composable
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.donate.DonateComponent

@Composable
fun DonateScreen(
    donateComponent: DonateComponent
) {
    /*val state by donateComponent.state.collectAsState()
    val sideEffect = donateComponent.sideEffect
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
        onBackButtonPressed = {
            donateComponent.backClicked()
        },
        onProductClicked = {
            //donateComponent.purchase(it)
        }
    )
}
