package work.racka.thinkrchive.v2.common.features.about.container

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.about.AboutSideEffect
import states.about.AboutState
import work.racka.thinkrchive.v2.common.features.about.data.AboutData
import work.racka.thinkrchive.v2.common.features.about.util.Constants

internal class AboutContainerImpl(
    private val aboutData: AboutData,
    private val scope: CoroutineScope,
) : AboutContainer {

    private val _state: MutableStateFlow<AboutState.State> = MutableStateFlow(AboutState.EmptyState)
    override val state: StateFlow<AboutState.State>
        get() = _state

    private val _sideEffect = Channel<AboutSideEffect>(capacity = Channel.UNLIMITED)
    override val sideEffect: Flow<AboutSideEffect>
        get() = _sideEffect.receiveAsFlow()

    init {
        getAppAbout()
    }

    private fun getAppAbout() {
        scope.launch {
            _state.update {
                it.copy(
                    appAbout = aboutData.getAppAboutData(),
                    hasUpdates = aboutData.hasUpdates()
                )
            }
            if (state.value.hasUpdates)
                _sideEffect.send(AboutSideEffect.UpdateFound(Constants.UPDATE_FOUND))
        }
    }

    override fun update() {
        scope.launch {
            _state.update {
                it.copy(
                    hasUpdates = aboutData.hasUpdates()
                )
            }
            if (!state.value.hasUpdates)
                _sideEffect.send(AboutSideEffect.UpdateNotFound(Constants.UPDATE_NOT_FOUND))
            else _sideEffect.send(AboutSideEffect.Update)
        }
    }
}