package work.racka.thinkrchive.v2.common.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

internal class TestDriverProvider(
    private val schema: SqlDriver.Schema
) : DriverProvider {
    override suspend fun providerDbDriver(): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { schema.create(it) }
    }
}