package work.racka.thinkrchive.v2.common.features.thinkpad_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.features.settings.AppSettings
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListContainerHost
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListContainerHostImpl
import work.racka.thinkrchive.v2.common.features.thinkpad_list.container.ThinkpadListHelper

actual class ThinkpadListViewModel(
    helper: ThinkpadListHelper,
    settings: AppSettings,
) : ViewModel() {

    val host: ThinkpadListContainerHost = ThinkpadListContainerHostImpl(
        helper = helper,
        settings = settings,
        scope = viewModelScope
    )
}
