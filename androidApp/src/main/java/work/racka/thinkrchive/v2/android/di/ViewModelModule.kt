package work.racka.thinkrchive.v2.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.ui.main.viewModel.DonateViewModel
import work.racka.thinkrchive.v2.android.ui.main.viewModel.ThinkpadSettingsViewModel

object ViewModelModule {

    fun module() = module {
        viewModel {
            DonateViewModel(
                billingRepository = get()
            )
        }

        viewModel {
            ThinkpadSettingsViewModel(
                prefDataStore = get()
            )
        }
    }
}