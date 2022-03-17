package work.racka.thinkrchive.v2.common.features.thinkpad_details.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState

interface ThinkpadDetailsContainerHost {
    val state: StateFlow<ThinkpadDetailsState>
    val sideEffect: Flow<ThinkpadDetailsSideEffect>
    fun getThinkpad(model: String?)
    fun openPsrefLink()
}