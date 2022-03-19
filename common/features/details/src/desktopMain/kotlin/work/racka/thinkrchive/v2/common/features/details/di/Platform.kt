package work.racka.thinkrchive.v2.common.features.details.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.details.viewmodel.ThinkpadDetailsViewModel

internal actual object Platform {
    actual fun platformDetailsModule() = module {
        factory { (model: String?) ->
            ThinkpadDetailsViewModel(
                repository = get(),
                s = model,
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}