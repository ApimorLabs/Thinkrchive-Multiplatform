package work.racka.thinkrchive.v2.common.settings.di

import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.settings.SettingsRepository

object Settings {

    fun KoinApplication.settingsModules() =
        this.apply {
            modules(
                commonModule(),
                platformSettingsModule()
            )
        }

    private fun commonModule() = module {
        single {
            val settings = Settings()
            SettingsRepository(settings)
        }
    }
}