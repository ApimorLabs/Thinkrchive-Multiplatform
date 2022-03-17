package work.racka.thinkrchive.v2.common.features.about.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerHost
import work.racka.thinkrchive.v2.common.features.about.container.AboutContainerHostImpl
import work.racka.thinkrchive.v2.common.features.about.data.AboutData

actual class AboutViewModel(
    aboutData: AboutData
) : ViewModel() {

    val host: AboutContainerHost by lazy {
        AboutContainerHostImpl(
            aboutData = aboutData,
            scope = viewModelScope
        )
    }
}