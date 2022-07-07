package work.racka.thinkrchive.v2.common.integration.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import work.racka.thinkrchive.v2.common.all_features.di.AllFeatures
import work.racka.thinkrchive.v2.common.billing.di.Billing
import work.racka.thinkrchive.v2.common.database.di.Database
import work.racka.thinkrchive.v2.common.network.di.Network
import work.racka.thinkrchive.v2.common.settings.di.MultiplatformSettings
import work.thinkrchive.v2.common.data.di.Data

object KoinMain {
    // This should be used in every target as a starting point for Koin
    fun initKoin(
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()

        // Features
        AllFeatures.run { installModules() }
        with(FeatureModules) {
            featureModules()
        }

        // Non feature Modules
        with(Data) {
            dataModules()
        }

        with(Network) {
            networkModules()
        }
        with(Database) {
            databaseModules()
        }
        with(MultiplatformSettings) {
            settingsModules()
        }
        with(Billing) {
            billingModules()
        }
    }
}