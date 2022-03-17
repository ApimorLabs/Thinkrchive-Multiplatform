package work.racka.thinkrchive.v2.common.features.details.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetailsContainerHost
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetailsContainerHostImpl
import work.racka.thinkrchive.v2.common.features.details.repository.DetailsRepository

actual class ThinkpadDetailsViewModel(
    repository: DetailsRepository,
    scope: CoroutineScope
) {
    val host: ThinkpadDetailsContainerHost = ThinkpadDetailsContainerHostImpl(
        repository = repository,
        scope = scope
    )
}