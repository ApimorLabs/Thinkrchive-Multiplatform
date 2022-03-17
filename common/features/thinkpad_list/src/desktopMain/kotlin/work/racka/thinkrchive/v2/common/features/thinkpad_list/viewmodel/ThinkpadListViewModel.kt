package work.racka.thinkrchive.v2.common.features.thinkpad_list.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListContainerHost
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListContainerHostImpl
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListHelper

actual class ThinkpadListViewModel(
    helper: ThinkpadListHelper,
    settings: AppSettings,
    scope: CoroutineScope
) {
    val host: ThinkpadListContainerHost by lazy {
        ThinkpadListContainerHostImpl(
            helper = helper,
            settings = settings,
            scope = scope
        )
    }
}