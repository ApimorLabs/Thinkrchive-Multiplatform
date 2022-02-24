package work.racka.thinkrchive.v2.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.billing.BillingManager
import work.racka.thinkrchive.v2.android.repository.BillingRepository
import work.racka.thinkrchive.v2.android.ui.main.viewModel.DonateViewModel

object BillingModule {

     fun module() = module {
         scope<DonateViewModel> {
             scoped {
                 BillingManager(androidContext())
             }

             scoped {
                 BillingRepository(
                     billingManager = get()
                 )
             }
        }
    }
}