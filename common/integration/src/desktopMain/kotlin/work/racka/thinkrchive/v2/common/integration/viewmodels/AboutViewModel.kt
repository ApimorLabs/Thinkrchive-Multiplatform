package work.racka.thinkrchive.v2.common.integration.viewmodels

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.about.data.AboutData
import work.racka.thinkrchive.v2.common.integration.containers.about.AboutContainerHost
import work.racka.thinkrchive.v2.common.integration.containers.about.AboutContainerHostImpl

actual class AboutViewModel(
    aboutData: AboutData,
    scope: CoroutineScope
) {
    val host: AboutContainerHost = AboutContainerHostImpl(
        aboutData = aboutData,
        scope = scope
    )
}