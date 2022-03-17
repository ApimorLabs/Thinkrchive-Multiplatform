package work.racka.thinkrchive.v2.common.features.about.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.about.viewmodel.AboutViewModel

internal actual object Platform {
    actual fun platformAboutModule() = module {
        single {
            AboutViewModel(
                aboutData = get(),
                scope = CoroutineScope(Dispatchers.Main)
            )
        }
    }
}