package work.racka.thinkrchive.v2.common.features.settings

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.settings.container.SettingsContainer
import work.racka.thinkrchive.v2.common.features.settings.container.SettingsContainerImpl
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

class AppSettings(
    settings: MultiplatformSettings,
    scope: CoroutineScope
) {
    val host: SettingsContainer by lazy {
        SettingsContainerImpl(
            settings = settings,
            scope = scope
        )
    }
}