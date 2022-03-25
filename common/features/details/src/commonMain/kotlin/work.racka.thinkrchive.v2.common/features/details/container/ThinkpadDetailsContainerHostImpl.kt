package work.racka.thinkrchive.v2.common.features.details.container

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.common.features.details.repository.DetailsRepository
import work.racka.thinkrchive.v2.common.features.details.util.Constants

internal class ThinkpadDetailsContainerHostImpl(
    private val repository: DetailsRepository,
    private val model: String?,
    scope: CoroutineScope
) : ThinkpadDetailsContainerHost, ContainerHost<ThinkpadDetailsState, ThinkpadDetailsSideEffect> {
    private val logger = Logger.apply {
        setLogWriters(CommonWriter())
        withTag("DetailsContainerHost")
    }
    override val container: Container<ThinkpadDetailsState, ThinkpadDetailsSideEffect> =
        scope.container(ThinkpadDetailsState.EmptyState) {
            logger.d { "Details Container Initialized" }
            getThinkpad()
        }

    override val state: StateFlow<ThinkpadDetailsState>
        get() = container.stateFlow

    override val sideEffect: Flow<ThinkpadDetailsSideEffect>
        get() = container.sideEffectFlow

    override fun getThinkpad() = intent {
        when (model) {
            null -> {
                reduce { ThinkpadDetailsState.EmptyState }
                postSideEffect(
                    ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_MODEL_NULL)
                )
            }
            else -> {
                repository.getThinkpad(model).collectLatest { thinkpad ->
                    when (thinkpad) {
                        null -> {
                            reduce { ThinkpadDetailsState.EmptyState }
                            postSideEffect(
                                ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_NOT_FOUND)
                            )
                        }
                        else -> reduce { ThinkpadDetailsState.State(thinkpad) }
                    }
                }
            }
        }
    }

    override fun openPsrefLink() = intent {
        when (state) {
            is ThinkpadDetailsState.State -> {
                val link = (state as ThinkpadDetailsState.State)
                    .thinkpad
                    .psrefLink
                postSideEffect(
                    ThinkpadDetailsSideEffect.OpenPsrefLink(link)
                )
            }
            else -> postSideEffect(
                ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.OPEN_LINK_ERROR)
            )
        }
    }
}