package work.racka.thinkrchive.v2.common.features.thinkpad_details.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.thinkpad_details.viewmodel.ThinkpadDetailsViewModel

internal actual object Platform {
    actual fun platformDetailsModule() = module {
        single {
            ThinkpadDetailsViewModel(
                repository = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}