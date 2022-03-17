package work.racka.thinkrchive.v2.common.features.about.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.about.data.AboutData
import work.racka.thinkrchive.v2.common.features.about.data.AboutDataImpl

object About {

    fun KoinApplication.aboutModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformAboutModule()
            )
        }

    private fun commonModule() = module {
        single<AboutData> {
            AboutDataImpl()
        }
    }
}