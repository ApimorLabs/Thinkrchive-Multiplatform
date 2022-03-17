package work.racka.thinkrchive.v2.common.features.about.viewmodel

import kotlinx.coroutines.CoroutineScope
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerHost
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerHostImpl
import work.racka.thinkrchive.v2.common.features.about.data.AboutData

actual class AboutViewModel(
    aboutData: AboutData,
    scope: CoroutineScope
) {
    val host: AboutContainerHost by lazy {
        AboutContainerHostImpl(
            aboutData = aboutData,
            scope = scope
        )
    }
}