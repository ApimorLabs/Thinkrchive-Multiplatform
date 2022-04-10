package work.racka.thinkrchive.v2.common.settings.di

import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettingsImpl

object MultiplatformSettings {

    fun KoinApplication.settingsModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformSettingsModule()
            )
        }

    private fun commonModule() = module {
        single<MultiplatformSettings> {
            val settings = Settings()
            MultiplatformSettingsImpl(settings)
        }
    }
}