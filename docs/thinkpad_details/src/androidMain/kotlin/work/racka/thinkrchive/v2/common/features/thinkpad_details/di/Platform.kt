package work.racka.thinkrchive.v2.common.features.thinkpad_details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.thinkpad_details.viewmodel.ThinkpadDetailsViewModel

internal actual object Platform {
    actual fun platformDetailsModule() = module {
        viewModel {
            ThinkpadDetailsViewModel(
                repository = get()
            )
        }
    }
}