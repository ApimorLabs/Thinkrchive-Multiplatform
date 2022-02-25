package work.racka.thinkrchive.v2.common.database.di

import org.koin.core.KoinApplication

object Database {

    fun KoinApplication.databaseModules() =
        this.apply {
            modules(
                platformDatabaseModule()
            )
        }
}