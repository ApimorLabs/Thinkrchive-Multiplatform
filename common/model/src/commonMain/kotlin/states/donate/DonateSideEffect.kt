package states.donate

import domain.Product

sealed class DonateSideEffect {
    data class LaunchPurchaseFlow(val item: Any) : DonateSideEffect()

    data class Error(val errorMsg: String) : DonateSideEffect()

    data class ProductAuthorized(val product: Product) : DonateSideEffect()

    data class ShowPurchaseSuccessToast(val msg: String) : DonateSideEffect()

    object Nothing : DonateSideEffect()
}
