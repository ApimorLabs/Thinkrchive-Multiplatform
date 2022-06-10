package work.racka.thinkrchive.v2.common.database

import work.racka.thinkrchive.v2.common.database.db.ThinkpadDatabase

internal class ThinkrchiveDatabaseProvider(
    private val driverProvider: DriverProvider
) {

    private var database: ThinkpadDatabase? = null

    private suspend fun initDatabase() {
        if (database == null) {
            val driver = driverProvider.providerDbDriver()
            database = ThinkpadDatabase(
                driver = driver
            )
        }
    }

    suspend operator fun <R> invoke(block: suspend (ThinkpadDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }
}