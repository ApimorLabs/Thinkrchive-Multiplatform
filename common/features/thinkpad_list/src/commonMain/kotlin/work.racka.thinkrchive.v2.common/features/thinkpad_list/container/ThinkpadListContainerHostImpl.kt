package work.racka.thinkrchive.v2.common.features.thinkpad_list.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import util.Resource
import work.racka.thinkrchive.v2.common.features.settings.AppSettings

internal class ThinkpadListContainerHostImpl(
    private val helper: ThinkpadListHelper,
    private val settings: AppSettings,
    scope: CoroutineScope
) : ThinkpadListContainerHost, ContainerHost<ThinkpadListState.State, ThinkpadListSideEffect> {

    override val container = scope
        .container<ThinkpadListState.State, ThinkpadListSideEffect>(ThinkpadListState.EmptyState) {
            refreshThinkpadList()
            getUserSortOption()
        }

    override val state: StateFlow<ThinkpadListState.State>
        get() = container.stateFlow

    override val sideEffect: Flow<ThinkpadListSideEffect>
        get() = container.sideEffectFlow

    private fun getUserSortOption() = intent {
        settings.host.state.collect { settingsState ->
            reduce { state.copy(sortOption = settingsState.sortValue) }
            getSortedThinkpadList()
        }
    }

    override fun sortSelected(sortOption: Int) = intent {
        reduce { state.copy(sortOption = sortOption) }
        getSortedThinkpadList()
    }

    // An empty string on model retrieves all Thinkpads in the database
    override fun getSortedThinkpadList(model: String) = intent {
        val list = helper.getThinkpadListSorted(model, state.sortOption)
        list.collect { thinkpadList ->
            reduce { state.copy(thinkpadList = thinkpadList) }
        }
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    override fun refreshThinkpadList() = intent {
        val result = helper.repository.getAllThinkpadsFromNetwork()
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(isLoading = false)
                    )
                    helper.refreshThinkpadList(thinkpads = resource.data!!)
                }
                is Resource.Error -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(
                            isLoading = false,
                            errorMsg = resource.message!!
                        )
                    )
                }
                is Resource.Loading -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(isLoading = true)
                    )
                }
                else -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(isLoading = true)
                    )
                }
            }
        }
    }
}