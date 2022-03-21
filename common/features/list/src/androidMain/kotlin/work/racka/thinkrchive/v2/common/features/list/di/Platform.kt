package work.racka.thinkrchive.v2.common.features.list.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel

internal actual object Platform {
    actual fun platformSettingsModule() = module {
        viewModel {
            ThinkpadListViewModel(
                helper = get(),
                settings = get()
            )
        }
    }
}