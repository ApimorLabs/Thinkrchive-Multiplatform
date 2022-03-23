package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState

interface HomePaneComponent {
    val state: StateFlow<ThinkpadListState.State>
    val sideEffect: Flow<ThinkpadListSideEffect>
    fun search(query: String)
    fun sortOptionClicked(sort: Int)
    fun settingsClicked()
    fun aboutClicked()
    fun donateClicked()
}