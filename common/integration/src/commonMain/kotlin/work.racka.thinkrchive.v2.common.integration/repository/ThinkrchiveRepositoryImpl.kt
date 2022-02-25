package work.racka.thinkrchive.v2.common.integration.repository

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import domain.Thinkpad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import util.Resource
import work.racka.thinkrchive.v2.common.database.di.ThinkrchiveDatabaseWrapper
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getAllThinkpadsFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadsAlphaAscendingFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadsHighPriceFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadsLowPriceFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadsNewestFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.getThinkpadsOldestFromDb
import work.racka.thinkrchive.v2.common.integration.util.Helpers.insertAllThinkpadsToDb
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi

class ThinkrchiveRepositoryImpl(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val coroutineScope: CoroutineScope = MainScope(),
    thinkpadDatabase: ThinkrchiveDatabaseWrapper
) : ThinkrchiveRepository {
    private val logger = Logger.withTag("ThinkrchiveRepository")

    private val thinkpadDatabaseQueries = thinkpadDatabase
        .instance?.thinkpadDatabaseQueries

    override suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<Thinkpad>>> =
        flow {
            logger.d { "getAllThinkpadsFromNetwork" }
            var replay = true
            while (replay) {
                emit(Resource.Loading())
                try {
                    val response = thinkrchiveApi.getThinkpads()
                    emit(Resource.Success(data = response))
                    replay = false
                } catch (e: Exception) {
                    logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                    emit(Resource.Error(message = "An error occurred: ${e.message}"))
                }
            }
        }

    override suspend fun refreshThinkpadList(response: List<Thinkpad>) {
        thinkpadDatabaseQueries?.insertAllThinkpadsToDb(response)
    }

    override suspend fun getAllThinkpads(): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getAllThinkpadsFromDb()
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>? {
        return thinkpadDatabaseQueries?.getThinkpadFromDb(thinkpadModel)
            ?.asFlow()
            ?.mapToOne(context = coroutineScope.coroutineContext)
    }

    override suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getThinkpadsAlphaAscendingFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getThinkpadsNewestFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getThinkpadsOldestFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getThinkpadsLowPriceFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDatabaseQueries?.getThinkpadsHighPriceFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())
}