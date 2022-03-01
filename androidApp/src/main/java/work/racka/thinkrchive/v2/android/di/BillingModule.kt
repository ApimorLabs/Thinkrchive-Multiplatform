package work.racka.thinkrchive.v2.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.billing.BillingManager
import work.racka.thinkrchive.v2.android.repository.BillingRepository

object BillingModule {

    fun module() = module {
        single {
            BillingManager(androidContext())
        }
        single {
            BillingRepository(
                billingManager = get()
            )
        }
    }
}