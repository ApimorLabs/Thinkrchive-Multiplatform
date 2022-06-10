package work.racka.thinkrchive.v2.common.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await

internal class TestDriverProvider(
    private val schema: SqlDriver.Schema
) : DriverProvider {
    override suspend fun providerDbDriver(): SqlDriver {
        return initSqlDriver(schema).await()
    }
}