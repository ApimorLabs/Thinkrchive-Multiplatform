package work.racka.thinkrchive.v2.common.integration.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import work.racka.thinkrchive.v2.common.billing.di.Billing
import work.racka.thinkrchive.v2.common.database.di.Database
import work.racka.thinkrchive.v2.common.network.di.Network
import work.racka.thinkrchive.v2.common.settings.di.Settings

object KoinMain {
    // This should be used in every target as a starting point for Koin
    fun initKoin(
        enableNetworkLogs: Boolean = false,
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()

        // Modules in common directory
        with(Network) {
            networkModules()
        }
        with(Database) {
            databaseModules()
        }
        with(Settings) {
            settingsModules()
        }
        with(Billing) {
            aboutModules()
        }
        with(Integration) {
            integrationModules()
        }
    }
}