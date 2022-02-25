package work.racka.thinkrchive.v2.common.integration.repository

import domain.Thinkpad
import kotlinx.coroutines.flow.Flow
import util.Resource

interface ThinkrchiveRepository {
    suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<Thinkpad>>>
    suspend fun refreshThinkpadList(response: List<Thinkpad>)
    suspend fun getAllThinkpads(): Flow<List<Thinkpad>>
    suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>?
    suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
}