package work.racka.thinkrchive.v2.android.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import work.racka.thinkrchive.v2.android.ui.main.screenStates.ThinkpadDetailsScreenState
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository

class ThinkpadDetailsViewModel(
    private val thinkpadRepository: ThinkrchiveRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ThinkpadDetailsScreenState>(
        value = ThinkpadDetailsScreenState.EmptyState
    )
    val uiState: StateFlow<ThinkpadDetailsScreenState>
        get() = _uiState

    fun getThinkpad(thinkpadModel: String?) {
        viewModelScope.launch {
            thinkpadRepository.getThinkpad(thinkpadModel!!)?.collect {
                _uiState.value = ThinkpadDetailsScreenState.ThinkpadDetail(it)
            }
        }
    }


}
