package work.racka.thinkrchive.v2.common.features.details.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.common.features.details.util.Constants
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

internal class ThinkpadDetailsImpl(
    private val repository: ListRepository,
    private val model: String?,
    private val scope: CoroutineScope
) : ThinkpadDetails {

    private val _state: MutableStateFlow<ThinkpadDetailsState> =
        MutableStateFlow(ThinkpadDetailsState.EmptyState)
    override val state: StateFlow<ThinkpadDetailsState>
        get() = _state

    private val _sideEffect = Channel<ThinkpadDetailsSideEffect>(capacity = Channel.UNLIMITED)
    override val sideEffect: Flow<ThinkpadDetailsSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        getThinkpad()
    }

    override fun getThinkpad() {
        scope.launch {
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

    override fun openPsrefLink() {
        scope.launch {
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