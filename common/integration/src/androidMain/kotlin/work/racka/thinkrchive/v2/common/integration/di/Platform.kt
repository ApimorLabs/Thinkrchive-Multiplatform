package work.racka.thinkrchive.v2.common.integration.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.integration.viewmodels.DonateViewModel
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadDetailsViewModel

internal actual object Platform {
    actual fun platformIntegrationModule() = module {

        viewModel {
            ThinkpadDetailsViewModel(
                repository = get()
            )
        }

        viewModel {
            DonateViewModel(
                api = get()
            )
        }
    }
}