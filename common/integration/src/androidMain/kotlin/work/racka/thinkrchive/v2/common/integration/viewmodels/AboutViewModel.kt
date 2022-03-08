package work.racka.thinkrchive.v2.common.integration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.about.data.AboutData
import work.racka.thinkrchive.v2.common.integration.containers.about.AboutContainerHost
import work.racka.thinkrchive.v2.common.integration.containers.about.AboutContainerHostImpl

actual class AboutViewModel(
    aboutData: AboutData
) : ViewModel() {

    val host: AboutContainerHost = AboutContainerHostImpl(
        aboutData = aboutData,
        scope = viewModelScope
    )
}