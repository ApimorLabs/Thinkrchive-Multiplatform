package work.racka.thinkrchive.v2.android.ui.main.screenStates

import com.android.billingclient.api.SkuDetails

sealed class DonateScreenState {
    data class Donate(
        val skuDetailsList: List<SkuDetails> = listOf()
    ) : DonateScreenState()

    companion object {
        val DefaultState = Donate()
    }
}
