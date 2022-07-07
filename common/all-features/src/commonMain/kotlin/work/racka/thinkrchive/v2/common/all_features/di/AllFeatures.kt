package work.racka.thinkrchive.v2.common.all_features.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.common.mvvm.koin.vm.commonViewModel
import work.racka.thinkrchive.v2.common.all_features.list.viewmodel.ThinkpadListViewModel

object AllFeatures {

    fun KoinApplication.installModules() = apply {
        modules(commonModule())
    }

    private fun commonModule() = module {
        commonViewModel {
            ThinkpadListViewModel(
                helper = get(),
                settingsRepo = get()
            )
        }
    }
}