package work.racka.thinkrchive.v2.common.integration.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.integration.viewmodels.AboutViewModel
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadDetailsViewModel
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadListViewModel

internal actual object Platform {
    actual fun platformIntegrationModule() = module {
        single {
            AboutViewModel(
                aboutData = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }

        single {
            ThinkpadDetailsViewModel(
                repository = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }

        single {
            ThinkpadListViewModel(
                helper = get(),
                backgroundDispatcher = Dispatchers.Default,
                settings = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}