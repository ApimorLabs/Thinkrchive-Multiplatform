package work.racka.thinkrchive.v2.common.integration.containers.donate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.donate.DonateSideEffect
import states.donate.DonateState

interface DonateContainerHost {
    val state: StateFlow<DonateState.State>
    val sideEffect: Flow<DonateSideEffect>
    fun purchase(index: Int)
    fun processPurchase(success: Boolean, errorMsg: String = "")
    fun detachSideEffect()
}