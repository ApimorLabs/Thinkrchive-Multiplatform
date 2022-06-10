package work.racka.thinkrchive.v2.common.features.details.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetailsContainerHost
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetailsContainerHostImpl
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

actual class ThinkpadDetailsViewModel(
    repository: ListRepository,
    model: String?,
    scope: CoroutineScope
) {
    val host: ThinkpadDetailsContainerHost = ThinkpadDetailsContainerHostImpl(
        repository = repository,
        model = model,
        scope = scope
    )
}