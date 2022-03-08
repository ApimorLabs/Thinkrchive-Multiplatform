package work.racka.thinkrchive.v2.common.integration.viewmodels

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import work.racka.thinkrchive.v2.common.integration.containers.list.ThinkpadListContainerHost
import work.racka.thinkrchive.v2.common.integration.containers.list.ThinkpadListContainerHostImpl
import work.racka.thinkrchive.v2.common.integration.containers.list.ThinkpadListHelper
import work.racka.thinkrchive.v2.common.integration.containers.settings.AppSettings

actual class ThinkpadListViewModel(
    helper: ThinkpadListHelper,
    backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    settings: AppSettings,
    scope: CoroutineScope
) {
    val host: ThinkpadListContainerHost = ThinkpadListContainerHostImpl(
        helper = helper,
        backgroundDispatcher = backgroundDispatcher,
        settings = settings,
        scope = scope
    )
}