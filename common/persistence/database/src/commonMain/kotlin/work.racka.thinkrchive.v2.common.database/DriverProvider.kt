package work.racka.thinkrchive.v2.common.database

import com.squareup.sqldelight.db.SqlDriver

internal interface DriverProvider {
    suspend fun providerDbDriver(): SqlDriver
}