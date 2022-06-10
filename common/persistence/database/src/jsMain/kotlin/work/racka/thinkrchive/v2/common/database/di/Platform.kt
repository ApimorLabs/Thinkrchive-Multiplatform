package work.racka.thinkrchive.v2.common.database.di

import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.JsDriverProvider
import work.racka.thinkrchive.v2.common.database.ThinkrchiveDatabaseProvider
import work.racka.thinkrchive.v2.common.database.db.ThinkpadDatabase

internal actual object Platform {
    actual fun platformDatabaseModule() = module {
        single {
            val driverProvider = JsDriverProvider(schema = ThinkpadDatabase.Schema)
            ThinkrchiveDatabaseProvider(driverProvider)
        }
    }
}
