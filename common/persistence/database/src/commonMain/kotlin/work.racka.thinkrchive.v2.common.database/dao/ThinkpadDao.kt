package work.racka.thinkrchive.v2.common.database.dao

import data.local.ThinkpadDatabaseObject
import data.remote.response.ThinkpadResponse
import kotlinx.coroutines.flow.Flow

interface ThinkpadDao {
    suspend fun insertAllThinkpads(response: List<ThinkpadResponse>)
    suspend fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>>
    suspend fun getThinkpad(thinkpadModel: String): Flow<ThinkpadDatabaseObject?>
    suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>>
    suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>>
    suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>>
    suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>>
    suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>>
}