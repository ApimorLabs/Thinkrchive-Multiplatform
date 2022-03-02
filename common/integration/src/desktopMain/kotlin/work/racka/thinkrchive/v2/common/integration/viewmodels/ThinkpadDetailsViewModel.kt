package work.racka.thinkrchive.v2.common.integration.viewmodels

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.integration.containers.details.ThinkpadDetailsContainerHost
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository

actual class ThinkpadDetailsViewModel(
    repository: ThinkrchiveRepository,
    scope: CoroutineScope
) {
    val host: ThinkpadDetailsContainerHost = ThinkpadDetailsContainerHost(
        repository = repository,
        scope = scope
    )

    val uiState = host.container.stateFlow
    val sideEffect = host.container.sideEffectFlow
}