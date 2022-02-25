package work.racka.thinkrchive.v2.android.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Thinkpad
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import util.Resource
import work.racka.thinkrchive.v2.android.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.v2.android.ui.main.screenStates.ThinkpadListScreenState
import work.racka.thinkrchive.v2.android.utils.getChipNamesList
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository

class ThinkpadListViewModel(
    private val thinkpadRepository: ThinkrchiveRepository,
    private val prefDataStore: PrefDataStore
) : ViewModel() {

    private val allThinkpads = MutableStateFlow<List<Thinkpad>>(listOf())
    private val availableThinkpadSeries = MutableStateFlow<List<String>>(listOf())
    private val networkLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow("")
    private val sortOption = MutableStateFlow(0)

    // This will combine the different Flows emitted in this ViewModel into a single state
    // that will be observed by the UI.
    // Compose mutableStateOf can also be used to provide something similar
    // But this approach ensures that it will work with regular Android Views OOB
    val uiState: StateFlow<ThinkpadListScreenState> = combine(
        allThinkpads,
        networkLoading,
        networkError,
        availableThinkpadSeries,
        sortOption
    ) { allThinkpads, networkLoading, networkError, availableThinkpadSeries, sortOption ->
        ThinkpadListScreenState.ThinkpadListScreen(
            thinkpadList = allThinkpads,
            networkLoading = networkLoading,
            networkError = networkError,
            thinkpadSeriesList = availableThinkpadSeries,
            sortOption = sortOption
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThinkpadListScreenState.Empty
    )

    init {
        refreshThinkpadList()
        getUserSortOption()
        //getNewThinkpadListFromDatabase()
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() {
        viewModelScope.launch {
            val result = thinkpadRepository.getAllThinkpadsFromNetwork()

            result.collect { resource ->
                Timber.d("loading ThinkpadList")
                when (resource) {
                    is Resource.Success -> {
                        networkLoading.value = false
                        thinkpadRepository.refreshThinkpadList(resource.data!!)
                    }
                    is Resource.Error -> {
                        networkLoading.value = false
                        networkError.value = resource.message!!
                    }
                    is Resource.Loading -> {
                        networkLoading.value = true
                    }
                    else -> {
                        networkLoading.value = true
                    }
                }
            }
        }
    }

    // Get the user's preferred Sorting option first the load data from the database
    private fun getUserSortOption() {
        viewModelScope.launch {
            prefDataStore.readSortOptionSetting.collect { sortValue ->
                sortOption.value = sortValue
                getNewThinkpadListFromDatabase()
            }
        }
    }

    // Makes sure fresh data is taken from the database even after a network refresh
    // Also takes search query and returns only the data needed
    fun getNewThinkpadListFromDatabase(query: String = "") {
        viewModelScope.launch {
            when (sortOption.value) {
                0 -> {
                    thinkpadRepository.getThinkpadsAlphaAscending(query)
                        .collect {
                            allThinkpads.value = it
                            if (
                                allThinkpads.value.size > availableThinkpadSeries.value.size
                                || allThinkpads.value.isEmpty()
                            ) {
                                availableThinkpadSeries.value =
                                    allThinkpads.value.getChipNamesList()
                            }
                        }
                }
                1 -> {
                    thinkpadRepository.getThinkpadsNewestFirst(query)
                        .collect {
                            allThinkpads.value = it
                        }
                }
                2 -> {
                    thinkpadRepository.getThinkpadsOldestFirst(query)
                        .collect {
                            allThinkpads.value = it
                        }
                }
                3 -> {
                    thinkpadRepository.getThinkpadsLowPriceFirst(query)
                        .collect {
                            allThinkpads.value = it
                        }
                }
                4 -> {
                    thinkpadRepository.getThinkpadsHighPriceFirst(query)
                        .collect {
                            allThinkpads.value = it
                        }
                }
            }
        }
    }

    // Set the sort option from the UI
    fun sortSelected(sort: Int) {
        sortOption.value = sort
        getNewThinkpadListFromDatabase()
    }
}