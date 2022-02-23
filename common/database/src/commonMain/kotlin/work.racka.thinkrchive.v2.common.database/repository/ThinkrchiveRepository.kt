package work.racka.thinkrchive.v2.common.database.repository

import kotlinx.coroutines.flow.Flow
import work.racka.thinkrchive.v2.common.database.model.Thinkpad

interface ThinkrchiveRepository {
    suspend fun getAllThinkpadsFromNetwork(): List<Thinkpad>
    suspend fun refreshThinkpadList()
    suspend fun getAllThinkpads(): Flow<List<Thinkpad>>
    suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>?
    suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
}