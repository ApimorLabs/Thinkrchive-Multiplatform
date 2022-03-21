package work.racka.thinkrchive.v2.common.integration.di

import org.koin.core.KoinApplication
import work.racka.thinkrchive.v2.common.features.about.di.About
import work.racka.thinkrchive.v2.common.features.details.di.ThinkpadDetails
import work.racka.thinkrchive.v2.common.features.donate.di.Donate
import work.racka.thinkrchive.v2.common.features.list.di.ThinkpadList
import work.racka.thinkrchive.v2.common.features.settings.di.UserPreferences

internal object FeatureModules {

    fun KoinApplication.featureModules() {
        this.apply {

            with(Donate) {
                donateModules()
            }

            with(About) {
                aboutModules()
            }

            with(ThinkpadList) {
                listModules()
            }

            with(ThinkpadDetails) {
                detailsModules()
            }

            with(UserPreferences) {
                appSettingsModules()
            }
        }
    }
}