package work.racka.thinkrchive.v2.common.features.list.container

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import util.Resource
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

internal class ThinkpadListContainerHostImpl(
    private val helper: ThinkpadListHelper,
    private val settingsRepo: MultiplatformSettings,
    scope: CoroutineScope
) : ThinkpadListContainerHost, ContainerHost<ThinkpadListState.State, ThinkpadListSideEffect> {
    private val logger = Logger.apply {
        setLogWriters(CommonWriter())
        withTag("ContainerHost")
    }

    override val container = scope
        .container<ThinkpadListState.State, ThinkpadListSideEffect>(ThinkpadListState.EmptyState) {
            logger.d { "Container Initialized" }
            refreshThinkpadList()
            getUserSortOption()
        }

    override val state: StateFlow<ThinkpadListState.State>
        get() = container.stateFlow

    override val sideEffect: Flow<ThinkpadListSideEffect>
        get() = container.sideEffectFlow

    private fun getUserSortOption() = intent {
        settingsRepo.sortFlow.collectLatest { sortOption ->
            reduce { state.copy(sortOption = sortOption) }
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
        logger.d { "Refresh Thinkpad Intent" }
        val result = helper.repository.getAllThinkpadsFromNetwork()
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    postSideEffect(
                        ThinkpadListSideEffect.Network(isLoading = false)
                    )
                    resource.data?.let { helper.refreshThinkpadList(it) }
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