package work.racka.thinkrchive.v2.common.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal class AndroidDriverProvider(
    private val schema: SqlDriver.Schema,
    private val context: Context,
    private val databaseName: String
) : DriverProvider {
    override suspend fun providerDbDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = schema,
            context = context,
            name = databaseName
        )
    }
}