package work.racka.thinkrchive.v2.common.database.repository

import kotlinx.coroutines.flow.Flow
import work.racka.thinkrchive.v2.common.database.model.Thinkpad

interface ThinkrchiveRepository {
    suspend fun getAllThinkpadsFromNetwork(): List<Thinkpad>
    suspend fun refreshThinkpadList()
    suspend fun getAllThinkpads(): Flow<List<Thinkpad>>
    suspend fun getThinkpad(thinkpad: String): Flow<Thinkpad>
}