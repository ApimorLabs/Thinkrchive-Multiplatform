package work.racka.thinkrchive.v2.desktop.ui.screens.donate

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.features.donate.viewmodel.DonateViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component

class DonateScreen(
    private val componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    private val viewModel: DonateViewModel by inject(DonateViewModel::class.java)

    @Composable
    override fun render() {
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
            onBackButtonPressed = onBackClicked,
            onProductClicked = {
                //viewModel.host.purchase(it)
            }
        )
    }
}

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
