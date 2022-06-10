package work.racka.thinkrchive.v2.common.database.di

import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.AndroidDriverProvider
import work.racka.thinkrchive.v2.common.database.DriverProvider
import work.racka.thinkrchive.v2.common.database.ThinkrchiveDatabaseProvider
import work.racka.thinkrchive.v2.common.database.db.ThinkpadDatabase
import work.racka.thinkrchive.v2.common.database.util.Constants

internal actual object Platform {
    actual fun platformDatabaseModule() = module {
        single {
            val driverProvider: DriverProvider = AndroidDriverProvider(
                schema = ThinkpadDatabase.Schema,
                context = get(),
                databaseName = Constants.THINKPAD_DATABASE
            )
            ThinkrchiveDatabaseProvider(driverProvider)
        }
    }
}
