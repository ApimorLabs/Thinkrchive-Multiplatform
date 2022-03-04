package work.racka.thinkrchive.v2.common.billing.di

import org.koin.core.KoinApplication
import org.koin.dsl.module

object Billing {

    fun KoinApplication.aboutModules() =
        this.apply {
            modules(
                commonModule(),
                work.racka.thinkrchive.v2.common.billing.di.Platform.platformBillingModule()
            )
        }

    private fun commonModule() = module {

    }
}