package work.racka.thinkrchive.v2.common.integration.viewmodels

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.about.data.AboutData
import work.racka.thinkrchive.v2.common.integration.containers.about.AboutContainerHost

actual class AboutViewModel(
    aboutData: AboutData,
    scope: CoroutineScope
) {
    val host: AboutContainerHost = AboutContainerHost(
        aboutData = aboutData,
        scope = scope
    )

    val uiState = host.container.stateFlow
    val sideEffect = host.container.sideEffectFlow
}