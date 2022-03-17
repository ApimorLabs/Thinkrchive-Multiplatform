package work.racka.thinkrchive.v2.common.features.thinkpad_details.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.thinkpad_details.container.ThinkpadDetailsContainerHost
import work.racka.thinkrchive.v2.common.features.thinkpad_details.container.ThinkpadDetailsContainerHostImpl
import work.racka.thinkrchive.v2.common.features.thinkpad_details.repository.DetailsRepository

actual class ThinkpadDetailsViewModel(
    repository: DetailsRepository,
    scope: CoroutineScope
) {
    val host: ThinkpadDetailsContainerHost = ThinkpadDetailsContainerHostImpl(
        repository = repository,
        scope = scope
    )
}