package work.racka.thinkrchive.v2.common.features.list.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainer
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainerImpl
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import work.thinkrchive.v2.common.data.repositories.helpers.ListRepositoryHelper

actual class ThinkpadListViewModel(
    helper: ListRepositoryHelper,
    settingsRepo: MultiplatformSettings,
    private val scope: CoroutineScope
) {
    val hostDesktop: ThinkpadListContainer by lazy {
        ThinkpadListContainerImpl(
            helper = helper,
            settingsRepo = settingsRepo,
            scope = scope
        )
    }

    fun destroy() {
        scope.cancel()
    }
}