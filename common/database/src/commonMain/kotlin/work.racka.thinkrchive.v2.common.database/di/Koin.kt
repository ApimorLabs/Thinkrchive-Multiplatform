package work.racka.thinkrchive.v2.common.database.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import work.racka.thinkrchive.v2.common.network.di.Network

object Koin {

    fun initKoin(
        enableNetworkLogs: Boolean = false,
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()

        // Modules
        with(Network) {
            networkModules()
        }
        with(Database) {
            databaseModules()
        }
    }
}