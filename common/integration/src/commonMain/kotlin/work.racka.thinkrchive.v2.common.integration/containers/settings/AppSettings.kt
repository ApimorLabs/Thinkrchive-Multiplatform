package work.racka.thinkrchive.v2.common.integration.containers.settings

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.settings.repository.SettingsRepository

class AppSettings(
    settings: SettingsRepository,
    scope: CoroutineScope
) {
    val host: SettingsContainerHost = SettingsContainerHostImpl(
        settings = settings,
        scope = scope
    )
}