package work.racka.thinkrchive.v2.common.features.about.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainer
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerImpl
import work.racka.thinkrchive.v2.common.features.about.data.AboutData

actual class AboutViewModel(
    aboutData: AboutData
) : ViewModel() {

    val host: AboutContainer by lazy {
        AboutContainerImpl(
            aboutData = aboutData,
            scope = viewModelScope
        )
    }
}