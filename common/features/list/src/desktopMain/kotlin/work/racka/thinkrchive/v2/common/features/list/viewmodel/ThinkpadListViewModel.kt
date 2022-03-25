package work.racka.thinkrchive.v2.common.features.list.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainerHost
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainerHostImpl
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListHelper
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

actual class ThinkpadListViewModel(
    helper: ThinkpadListHelper,
    settingsRepo: MultiplatformSettings,
    scope: CoroutineScope
) {
    val hostDesktop: ThinkpadListContainerHost by lazy {
        ThinkpadListContainerHostImpl(
            helper = helper,
            settingsRepo = settingsRepo,
            scope = scope
        )
    }
}