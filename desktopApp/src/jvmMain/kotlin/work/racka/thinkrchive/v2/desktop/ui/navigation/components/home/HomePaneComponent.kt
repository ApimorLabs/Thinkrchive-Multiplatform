package work.racka.thinkrchive.v2.desktop.ui.navigation.components.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.PaneConfig

interface HomePaneComponent {
    val state: StateFlow<ThinkpadListState.State>
    val sideEffect: Flow<ThinkpadListSideEffect>
    val paneState: StateFlow<PaneConfig>
    fun updatePaneState(config: PaneConfig)
    fun search(query: String)
    fun sortOptionClicked(sort: Int)
    fun settingsClicked()
    fun aboutClicked()
    fun donateClicked()
}