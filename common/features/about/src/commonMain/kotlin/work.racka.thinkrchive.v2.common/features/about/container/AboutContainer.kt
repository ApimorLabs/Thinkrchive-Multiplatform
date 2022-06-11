package work.racka.thinkrchive.v2.common.features.about.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.about.AboutSideEffect
import states.about.AboutState

interface AboutContainer {
    val state: StateFlow<AboutState.State>
    val sideEffect: Flow<AboutSideEffect>
    fun update()
}