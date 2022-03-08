package work.racka.thinkrchive.v2.common.integration.containers.donate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.thinkrchive.v2.common.billing.api.BillingApi

internal actual class DonateContainerHostImpl(
    private val api: BillingApi,
    scope: CoroutineScope
) : DonateContainerHost, ContainerHost<DonateState.State, DonateSideEffect> {
    override val container: Container<DonateState.State, DonateSideEffect> =
        scope.container(DonateState.EmptyState)

    override val state: StateFlow<DonateState.State>
        get() = container.stateFlow

    override val sideEffect: Flow<DonateSideEffect>
        get() = container.sideEffectFlow

    override fun purchase(index: Int) = intent {

    }

    override fun processPurchase(success: Boolean, errorMsg: String) = intent {

    }

    override fun detachSideEffect() = intent {

    }
}