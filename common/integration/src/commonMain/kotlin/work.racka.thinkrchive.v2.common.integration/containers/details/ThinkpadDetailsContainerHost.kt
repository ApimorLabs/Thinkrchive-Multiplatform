package work.racka.thinkrchive.v2.common.integration.containers.details

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.details.ThinkpadDetailsSideEffect
import states.details.ThinkpadDetailsState
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository
import work.racka.thinkrchive.v2.common.integration.util.Constants

class ThinkpadDetailsContainerHost(
    private val repository: ThinkrchiveRepository,
    scope: CoroutineScope
) : ContainerHost<ThinkpadDetailsState, ThinkpadDetailsSideEffect> {

    override val container: Container<ThinkpadDetailsState, ThinkpadDetailsSideEffect> =
        scope.container(ThinkpadDetailsState.EmptyState)

    fun getThinkpad(model: String?) = intent {
        when (val result = repository.getThinkpad(model!!)) {
            null -> {
                reduce { ThinkpadDetailsState.EmptyState }
                postSideEffect(
                    ThinkpadDetailsSideEffect.DisplayErrorMsg(Constants.THINKPAD_NOT_FOUND)
                )
            }
            else -> {
                result.collect { thinkpad ->
                    reduce {
                        ThinkpadDetailsState.State(thinkpad)
                    }
                }
            }
        }
    }

    fun openPsrefLink() = intent {
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