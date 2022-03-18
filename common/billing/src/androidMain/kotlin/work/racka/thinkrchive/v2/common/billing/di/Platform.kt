package work.racka.thinkrchive.v2.common.billing.di

import com.revenuecat.purchases.Purchases
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.billing.api.BillingApiImpl

internal actual object Platform {
    actual fun platformBillingModule() = module {
        factory<BillingApi> {
            BillingApiImpl(
                purchases = Purchases.sharedInstance
            )
        }
    }
}