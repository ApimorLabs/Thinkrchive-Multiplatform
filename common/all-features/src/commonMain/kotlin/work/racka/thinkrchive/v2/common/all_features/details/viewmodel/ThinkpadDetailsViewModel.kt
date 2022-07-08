package work.racka.thinkrchive.v2.common.all_features.details.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.common.mvvm.viewmodel.CommonViewModel
import work.racka.thinkrchive.v2.common.all_features.util.Constants
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

class ThinkpadDetailsViewModel(
    private val repository: ListRepository,
    private val model: String?
) : CommonViewModel() {

    private val _state: MutableStateFlow<ThinkpadDetailsState> =
        MutableStateFlow(ThinkpadDetailsState.EmptyState)
    val state: StateFlow<ThinkpadDetailsState>
        get() = _state

    private val _sideEffect = Channel<ThinkpadDetailsSideEffect>(capacity = Channel.UNLIMITED)
    val sideEffect: Flow<ThinkpadDetailsSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        getThinkpad()
    }

    fun getThinkpad() {
        vmScope.launch {
            when (model) {
                null -> {
                    _state.update { ThinkpadDetailsState.EmptyState }
                    _sideEffect.send(
                        ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_MODEL_NULL)
                    )
                }
                else -> {
                    repository.getThinkpad(model).collectLatest { thinkpad ->
                        when (thinkpad) {
                            null -> {
                                _state.update { ThinkpadDetailsState.EmptyState }
                                _sideEffect.send(
                                    ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_NOT_FOUND)
                                )
                            }
                            else -> _state.update { ThinkpadDetailsState.State(thinkpad) }
                        }
                    }
                }
            }
        }
    }

    fun openPsrefLink() {
        vmScope.launch {
            when (state.value) {
                is ThinkpadDetailsState.State -> {
                    val link = (state.value as ThinkpadDetailsState.State)
                        .thinkpad
                        .psrefLink
                    _sideEffect.send(
                        ThinkpadDetailsSideEffect.OpenPsrefLink(link)
                    )
                }
                else -> _sideEffect.send(
                    ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.OPEN_LINK_ERROR)
                )
            }
        }
    }
}