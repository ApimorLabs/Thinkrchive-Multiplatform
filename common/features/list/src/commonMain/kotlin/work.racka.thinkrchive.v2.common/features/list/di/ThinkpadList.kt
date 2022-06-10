package work.racka.thinkrchive.v2.common.features.list.di

import org.koin.core.KoinApplication
import org.koin.dsl.module

object ThinkpadList {

    fun KoinApplication.listModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformListModule()
            )
        }

    private fun commonModule() = module { }
}