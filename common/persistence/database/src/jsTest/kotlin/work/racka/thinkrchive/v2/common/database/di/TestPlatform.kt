package work.racka.thinkrchive.v2.common.database.di

import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.TestDriverProvider
import work.racka.thinkrchive.v2.common.database.ThinkrchiveDatabaseProvider
import work.racka.thinkrchive.v2.common.database.db.ThinkpadDatabase

internal actual object TestPlatform {
    actual fun testPlatformDatabaseModule() = module {
        single {
            val driverProvider = TestDriverProvider(schema = ThinkpadDatabase.Schema)
            ThinkrchiveDatabaseProvider(driverProvider)
        }
    }
}
