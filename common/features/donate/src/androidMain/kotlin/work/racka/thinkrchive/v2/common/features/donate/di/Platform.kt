package work.racka.thinkrchive.v2.common.features.donate.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.donate.viewmodel.DonateViewModel

internal actual object Platform {
    actual fun platformSettingsModule() = module {
        viewModel {
            DonateViewModel(
                api = get()
            )
        }
    }
}