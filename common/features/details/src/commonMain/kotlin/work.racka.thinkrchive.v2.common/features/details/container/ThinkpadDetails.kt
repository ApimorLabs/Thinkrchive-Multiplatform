package work.racka.thinkrchive.v2.common.features.details.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState

interface ThinkpadDetails {
    val state: StateFlow<ThinkpadDetailsState>
    val sideEffect: Flow<ThinkpadDetailsSideEffect>
    fun getThinkpad()
    fun openPsrefLink()
}