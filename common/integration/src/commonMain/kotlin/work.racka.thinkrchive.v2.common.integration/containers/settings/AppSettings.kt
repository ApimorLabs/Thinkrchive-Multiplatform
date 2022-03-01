package work.racka.thinkrchive.v2.common.integration.containers.settings

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.settings.SettingsRepository

class AppSettings(
    settings: SettingsRepository,
    scope: CoroutineScope
) {
    val host: ThinkpadSettingsContainerHost = ThinkpadSettingsContainerHost(
        settings = settings,
        scope = scope
    )

    val state = host.container.stateFlow
    val sideEffect = host.container.sideEffectFlow
}