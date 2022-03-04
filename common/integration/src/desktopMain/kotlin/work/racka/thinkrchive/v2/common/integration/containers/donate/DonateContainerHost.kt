package work.racka.thinkrchive.v2.common.integration.containers.donate

import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.thinkrchive.v2.common.billing.api.BillingApi

actual class DonateContainerHost(
    private val api: BillingApi,
    scope: CoroutineScope
) : ContainerHost<DonateState.State, DonateSideEffect> {
    override val container: Container<DonateState.State, DonateSideEffect> =
        scope.container(DonateState.EmptyState)
}