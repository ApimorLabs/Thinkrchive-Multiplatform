package work.racka.thinkrchive.v2.common.all_features.donate.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.common.mvvm.viewmodel.CommonViewModel

expect class DonateViewModel : CommonViewModel {
    val state: StateFlow<DonateState.State>
    val sideEffect: Flow<DonateSideEffect>
    fun purchase(index: Int)
    fun processPurchase(success: Boolean, errorMsg: String = "")
}