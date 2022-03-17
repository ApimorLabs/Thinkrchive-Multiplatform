package work.racka.thinkrchive.v2.common.features.details.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal actual object Platform {
    actual fun platformDetailsModule() = module {
        single {
            work.racka.thinkrchive.v2.common.features.details.viewmodel.ThinkpadDetailsViewModel(
                repository = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}