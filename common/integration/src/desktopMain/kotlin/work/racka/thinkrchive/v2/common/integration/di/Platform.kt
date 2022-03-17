package work.racka.thinkrchive.v2.common.integration.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadDetailsViewModel

internal actual object Platform {
    actual fun platformIntegrationModule() = module {

        single {
            ThinkpadDetailsViewModel(
                repository = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}