package work.racka.thinkrchive.v2.common.database.repository

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import work.racka.thinkrchive.v2.common.database.model.Thinkpad
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApi

class ThinkrchiveRepositoryImpl(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val coroutineScope: CoroutineScope,
) : ThinkrchiveRepository {
    private val logger = Logger.withTag("ThinkrchiveRepository")

    init {
        coroutineScope.launch {
            refreshThinkpadList()
        }
    }

    override suspend fun getAllThinkpadsFromNetwork(): List<Thinkpad> =
        thinkrchiveApi.getThinkpads()

    override suspend fun refreshThinkpadList() {
        logger.d { "getAllThinkpadsFromNetwork" }
        try {
            val response = this.getAllThinkpadsFromNetwork()


        } catch (e: Exception) {
            logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
        }
    }

    override suspend fun getAllThinkpads(): Flow<List<Thinkpad>> {
        TODO("Not yet implemented")
    }

    override suspend fun getThinkpad(thinkpad: String): Flow<Thinkpad> {
        TODO("Not yet implemented")
    }
}