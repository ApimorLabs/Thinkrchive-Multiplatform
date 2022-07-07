package work.racka.thinkrchive.v2.common.all_features.di

import org.koin.core.module.Module
import org.koin.dsl.module
import work.racka.common.mvvm.koin.vm.commonViewModel
import work.racka.thinkrchive.v2.common.all_features.donate.viewmodel.DonateViewModel

internal actual object Platform {
    actual fun platformFeaturesModule(): Module = module {
        commonViewModel {
            DonateViewModel(
                api = get()
            )
        }
    }
}