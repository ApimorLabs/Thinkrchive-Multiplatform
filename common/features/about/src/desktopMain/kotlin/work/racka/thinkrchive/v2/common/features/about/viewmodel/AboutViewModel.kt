package work.racka.thinkrchive.v2.common.features.about.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainer
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerImpl
import work.racka.thinkrchive.v2.common.features.about.data.AboutData

actual class AboutViewModel(
    aboutData: AboutData,
    private val scope: CoroutineScope
) {
    val host: AboutContainer by lazy {
        AboutContainerImpl(
            aboutData = aboutData,
            scope = scope
        )
    }

    fun destroy() {
        scope.cancel()
    }
}