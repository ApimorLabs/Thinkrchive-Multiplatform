package work.racka.thinkrchive.v2.common.all_features.list.viewmodel

import data.remote.response.ThinkpadResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import util.NetworkError
import util.Resource
import work.racka.common.mvvm.viewmodel.CommonViewModel
import work.racka.thinkrchive.v2.common.all_features.util.Constants
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import work.thinkrchive.v2.common.data.repositories.helpers.ListRepositoryHelper

class ThinkpadListViewModel(
    private val helper: ListRepositoryHelper,
    private val settingsRepo: MultiplatformSettings,
) : CommonViewModel() {

    private val _state: MutableStateFlow<ThinkpadListState.State> =
        MutableStateFlow(ThinkpadListState.EmptyState)
    val state: StateFlow<ThinkpadListState.State>
        get() = _state

    private val _sideEffect = Channel<ThinkpadListSideEffect>(capacity = Channel.UNLIMITED)
    val sideEffect: Flow<ThinkpadListSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        refreshThinkpadList()
        getUserSortOption()
    }

    private fun getUserSortOption() {
        vmScope.launch {
            settingsRepo.sortFlow.collectLatest { sortOption ->
                _state.update { it.copy(sortOption = sortOption) }
                getSortedThinkpadList()
            }
        }
    }

    fun sortSelected(sortOption: Int) {
        vmScope.launch {
            _state.update { it.copy(sortOption = sortOption) }
            getSortedThinkpadList()
        }
    }

    // An empty string on model retrieves all Thinkpads in the database
    fun getSortedThinkpadList(model: String = "") {
        vmScope.launch {
            val list = helper.getThinkpadListSorted(model, state.value.sortOption)
            list.collect { thinkpadList ->
                _state.update { it.copy(thinkpadList = thinkpadList) }
            }
        }
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() {
        vmScope.launch {
            val result: Flow<Resource<List<ThinkpadResponse>, NetworkError>> =
                helper.repository.getAllThinkpadsFromNetwork()
            result.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.update { it.copy(networkLoading = false) }
                        resource.data?.let { helper.refreshThinkpadList(it) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(networkLoading = false) }
                        when (resource.errorCode) {
                            NetworkError.StatusCodeError -> {
                                _sideEffect.send(
                                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                                        msg = Constants.RESPONSE_CODE_ERROR,
                                        networkError = resource.errorCode!!
                                    )
                                )
                            }
                            NetworkError.SerializationError -> {
                                _sideEffect.send(
                                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                                        msg = Constants.SERIALIZATION_ERROR,
                                        networkError = resource.errorCode!!
                                    )
                                )
                            }
                            NetworkError.NoInternetError -> {
                                _sideEffect.send(
                                    ThinkpadListSideEffect.ShowNetworkErrorSnackbar(
                                        msg = Constants.NO_INTERNET_ERROR,
                                        networkError = resource.errorCode!!
                                    )
                                )
                            }
                            else -> {}
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(networkLoading = true) }
                    }
                    else -> {
                        _state.update { it.copy(networkLoading = false) }
                    }
                }
            }
        }
    }
}