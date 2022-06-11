package work.racka.thinkrchive.v2.common.features.donate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.features.donate.container.DonateContainer
import work.racka.thinkrchive.v2.common.features.donate.container.DonateContainerImpl

actual class DonateViewModel(
    api: BillingApi
) : ViewModel() {
    val host: DonateContainer = DonateContainerImpl(
        api = api,
        scope = viewModelScope
    )
}