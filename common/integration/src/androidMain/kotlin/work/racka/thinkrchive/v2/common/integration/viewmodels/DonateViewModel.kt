package work.racka.thinkrchive.v2.common.integration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.integration.containers.donate.DonateContainerHost

actual class DonateViewModel(
    api: BillingApi
) : ViewModel() {
    val host: DonateContainerHost = DonateContainerHost(
        api = api,
        scope = viewModelScope
    )

    val uiState = host.container.stateFlow
    val sideEffect = host.container.sideEffectFlow
}