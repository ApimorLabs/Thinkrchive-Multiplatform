package work.racka.thinkrchive.v2.common.features.thinkpad_list.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.thinkpad_list.viewmodel.ThinkpadListViewModel

internal actual object Platform {
    actual fun platformSettingsModule() = module {
        single {
            ThinkpadListViewModel(
                helper = get(),
                settings = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}