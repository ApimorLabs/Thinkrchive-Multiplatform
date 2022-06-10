package work.racka.thinkrchive.v2.common.integration.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import work.racka.thinkrchive.v2.common.billing.di.Billing
import work.racka.thinkrchive.v2.common.database.di.Database
import work.racka.thinkrchive.v2.common.network.di.Network
import work.racka.thinkrchive.v2.common.settings.di.MultiplatformSettings

object KoinMain {
    // This should be used in every target as a starting point for Koin
    fun initKoin(
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()

        // Feature modules
        with(FeatureModules) {
            featureModules()
        }

        // All non feature Modules
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