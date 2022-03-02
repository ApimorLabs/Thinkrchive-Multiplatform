package work.racka.thinkrchive.v2.common.about.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.about.data.AboutData
import work.racka.thinkrchive.v2.common.about.data.AboutDataImpl

object About {

    fun KoinApplication.aboutModules() =
        this.apply {
            modules(
                commonModule(),
                platformAboutModule()
            )
        }

    private fun commonModule() = module {
        single<AboutData> {
            AboutDataImpl()
        }
    }
}