package work.racka.thinkrchive.v2.common.features.about.container

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.about.AboutSideEffect
import states.about.AboutState
import work.racka.thinkrchive.v2.common.features.about.data.AboutData
import work.racka.thinkrchive.v2.common.features.about.util.Constants

internal class AboutContainerHostImpl(
    private val aboutData: AboutData,
    scope: CoroutineScope,
) : AboutContainerHost, ContainerHost<AboutState.State, AboutSideEffect> {

    private val logger = Logger.apply {
        setLogWriters(CommonWriter())
        withTag("AboutContainerHost")
    }
    override val container: Container<AboutState.State, AboutSideEffect> =
        scope.container(AboutState.EmptyState) {
            logger.d { "Container Host initialized" }
            getAppAbout()
        }

    override val state: StateFlow<AboutState.State>
        get() = container.stateFlow

    override val sideEffect: Flow<AboutSideEffect>
        get() = container.sideEffectFlow

    private fun getAppAbout() = intent {
        reduce {
            state.copy(
                appAbout = aboutData.getAppAboutData(),
                hasUpdates = aboutData.hasUpdates()
            )
        }
        if (state.hasUpdates)
            postSideEffect(AboutSideEffect.UpdateFound(Constants.UPDATE_FOUND))
    }

    override fun update() = intent {
        reduce {
            state.copy(
                hasUpdates = aboutData.hasUpdates()
            )
        }
        if (!state.hasUpdates)
            postSideEffect(AboutSideEffect.UpdateNotFound(Constants.UPDATE_NOT_FOUND))
        else postSideEffect(AboutSideEffect.Update)
    }
}