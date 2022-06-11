package work.racka.thinkrchive.v2.common.features.donate.di

import org.koin.core.KoinApplication
import org.koin.dsl.module

object Donate {

    fun KoinApplication.donateModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformSettingsModule()
            )
        }

    private fun commonModule() = module { }
}