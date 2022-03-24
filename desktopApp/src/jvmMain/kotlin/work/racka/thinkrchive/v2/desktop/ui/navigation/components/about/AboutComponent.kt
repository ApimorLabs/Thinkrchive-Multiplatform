package work.racka.thinkrchive.v2.desktop.ui.navigation.components.about

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.about.AboutSideEffect
import states.about.AboutState

interface AboutComponent {
    val state: StateFlow<AboutState.State>
    val sideEffect: Flow<AboutSideEffect>
    fun backClicked()
    fun update()
}