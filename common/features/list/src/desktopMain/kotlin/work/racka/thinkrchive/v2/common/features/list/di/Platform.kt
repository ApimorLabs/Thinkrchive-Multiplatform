package work.racka.thinkrchive.v2.common.features.list.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel

internal actual object Platform {
    actual fun platformListModule() = module {
        factory {
            ThinkpadListViewModel(
                helper = get(),
                settingsRepo = get(),
                scope = CoroutineScope(Dispatchers.Main.immediate)
            )
        }
    }
}