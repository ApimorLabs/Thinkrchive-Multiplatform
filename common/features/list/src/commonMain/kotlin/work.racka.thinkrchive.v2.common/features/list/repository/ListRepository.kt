package work.racka.thinkrchive.v2.common.features.list.repository

import data.remote.response.ThinkpadResponse
import domain.Thinkpad
import kotlinx.coroutines.flow.Flow
import util.NetworkError
import util.Resource

interface ListRepository {
    suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<ThinkpadResponse>, NetworkError>>
    suspend fun refreshThinkpadList(response: List<ThinkpadResponse>)
    fun getAllThinkpads(): Flow<List<Thinkpad>>
    fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>>
    fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
    fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>>
}