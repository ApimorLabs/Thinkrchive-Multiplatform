package work.racka.thinkrchive.v2.common.features.thinkpad_list.container

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState

interface ThinkpadListContainerHost {
    val state: StateFlow<ThinkpadListState.State>
    val sideEffect: Flow<ThinkpadListSideEffect>
    fun sortSelected(sortOption: Int)
    fun getSortedThinkpadList(model: String = "")
    fun refreshThinkpadList()
}