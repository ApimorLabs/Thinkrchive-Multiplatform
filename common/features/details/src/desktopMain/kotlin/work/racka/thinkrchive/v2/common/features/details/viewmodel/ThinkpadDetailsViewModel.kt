package work.racka.thinkrchive.v2.common.features.details.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetails
import work.racka.thinkrchive.v2.common.features.details.container.ThinkpadDetailsImpl
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

actual class ThinkpadDetailsViewModel(
    repository: ListRepository,
    model: String?,
    scope: CoroutineScope
) {
    val host: ThinkpadDetails = ThinkpadDetailsImpl(
        repository = repository,
        model = model,
        scope = scope
    )
}