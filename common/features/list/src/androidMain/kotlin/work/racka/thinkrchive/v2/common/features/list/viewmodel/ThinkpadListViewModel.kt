package work.racka.thinkrchive.v2.common.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainerHost
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListContainerHostImpl
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import work.thinkrchive.v2.common.data.repositories.helpers.ListRepositoryHelper

actual class ThinkpadListViewModel(
    helper: ListRepositoryHelper,
    settingsRepo: MultiplatformSettings,
) : ViewModel() {

    val host: ThinkpadListContainerHost by lazy {
        ThinkpadListContainerHostImpl(
            helper = helper,
            settingsRepo = settingsRepo,
            scope = viewModelScope
        )
    }
}
