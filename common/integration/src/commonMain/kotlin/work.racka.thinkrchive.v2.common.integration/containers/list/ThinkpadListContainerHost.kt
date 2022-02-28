package work.racka.thinkrchive.v2.common.integration.containers.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import util.Resource
import work.racka.thinkrchive.v2.common.settings.SettingsRepository

class ThinkpadListContainerHost(
    private val helper: ThinkpadListHelper,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val settings: SettingsRepository,
    scope: CoroutineScope
) : ContainerHost<ThinkpadListState.State, ThinkpadListSideEffect> {

    override val container = scope
        .container<ThinkpadListState.State, ThinkpadListSideEffect>(ThinkpadListState.EmptyState) {
            refreshThinkpadList()
            getUserSortOption()
        }

    private fun getUserSortOption() = intent {
        val sortOption = settings.readSortSettings()
        reduce { state.copy(sortOption = sortOption) }
        getSortedThinkpadList()
    }

    fun sortSelected(sortOption: Int) = intent {
        reduce { state.copy(sortOption = sortOption) }
        getSortedThinkpadList()
    }

    // An empty string on model retrieves all Thinkpads in the database
    fun getSortedThinkpadList(model: String = "") = intent {
        val list = helper.getThinkpadListSorted(model, state.sortOption)
        list.collect { thinkpadList ->
            reduce { state.copy(thinkpadList = thinkpadList) }
        }
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() = intent {
        val result = helper.repository.getAllThinkpadsFromNetwork()
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(isLoading = false)
                    )
                    helper.refreshThinkpadList(
                        thinkpads = resource.data!!,
                        dispatcher = backgroundDispatcher
                    )
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