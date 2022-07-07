package work.racka.thinkrchive.v2.common.all_features.donate.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.common.mvvm.viewmodel.CommonViewModel

actual class DonateViewModel(
) : CommonViewModel() {
    private val _state: MutableStateFlow<DonateState.State> =
        MutableStateFlow(DonateState.EmptyState)
    actual val state: StateFlow<DonateState.State>
        get() = _state

    private val _sideEffect = Channel<DonateSideEffect>(capacity = Channel.UNLIMITED)
    actual val sideEffect: Flow<DonateSideEffect>
        get() = _sideEffect.receiveAsFlow()

    actual fun purchase(index: Int) {

    }

    actual fun processPurchase(success: Boolean, errorMsg: String) {

    }
}