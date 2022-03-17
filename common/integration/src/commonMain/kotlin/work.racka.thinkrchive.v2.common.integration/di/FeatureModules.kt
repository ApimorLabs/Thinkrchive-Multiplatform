package work.racka.thinkrchive.v2.common.integration.di

import org.koin.core.KoinApplication
import work.racka.thinkrchive.v2.common.features.about.di.About
import work.racka.thinkrchive.v2.common.features.settings.di.Settings

internal object FeatureModules {

    fun KoinApplication.featureModules() {
        this.apply {

            with(Settings) {
                appSettingsModules()
            }

            with(About) {
                aboutModules()
            }
        }
    }
}