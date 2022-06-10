package work.racka.thinkrchive.v2.common.features.details.di

import org.koin.core.KoinApplication
import org.koin.dsl.module

object ThinkpadDetails {

    fun KoinApplication.detailsModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformDetailsModule()
            )
        }

    private fun commonModule() = module { }
}