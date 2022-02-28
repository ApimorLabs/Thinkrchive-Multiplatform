package work.racka.thinkrchive.v2.common.integration.di

import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadListViewModel

internal actual fun platformIntegrationModule() = module {
    viewModel {
        ThinkpadListViewModel(
            helper = get(),
            backgroundDispatcher = Dispatchers.IO,
            settings = get()
        )
    }
}