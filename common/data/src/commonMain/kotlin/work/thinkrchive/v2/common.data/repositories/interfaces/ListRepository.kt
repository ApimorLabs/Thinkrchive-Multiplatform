package work.thinkrchive.v2.common.data.repositories.interfaces

import data.remote.response.ThinkpadResponse
import domain.Thinkpad
import kotlinx.coroutines.flow.Flow
import util.NetworkError
import util.Resource

interface ListRepository {
    suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<ThinkpadResponse>, NetworkError>>
    suspend fun refreshThinkpadList(response: List<ThinkpadResponse>)
    suspend fun getAllThinkpads(): Flow<List<Thinkpad>>
    suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad?>
}