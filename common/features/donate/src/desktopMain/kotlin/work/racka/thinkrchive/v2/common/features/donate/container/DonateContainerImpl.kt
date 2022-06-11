package work.racka.thinkrchive.v2.common.features.donate.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.thinkrchive.v2.common.billing.api.BillingApi

internal class DonateContainerImpl(
    private val api: BillingApi,
    private val scope: CoroutineScope
) : DonateContainer {

    private val _state: MutableStateFlow<DonateState.State> =
        MutableStateFlow(DonateState.EmptyState)
    override val state: StateFlow<DonateState.State>
        get() = _state

    private val _sideEffect = Channel<DonateSideEffect>(capacity = Channel.UNLIMITED)
    override val sideEffect: Flow<DonateSideEffect>
        get() = _sideEffect.receiveAsFlow()

    override fun purchase(index: Int) {

    }

    override fun processPurchase(success: Boolean, errorMsg: String) {

    }
}