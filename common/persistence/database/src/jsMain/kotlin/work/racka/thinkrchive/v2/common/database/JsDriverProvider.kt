package work.racka.thinkrchive.v2.common.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await

internal class JsDriverProvider(private val schema: SqlDriver.Schema) : DriverProvider {
    override suspend fun providerDbDriver(): SqlDriver {
        return initSqlDriver(schema).await()
    }
}