package work.racka.thinkrchive.v2.common.integration.repository

import co.touchlab.kermit.Logger
import data.remote.response.ThinkpadResponse
import domain.Thinkpad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import util.DataMappers.asDomainModel
import util.DataMappers.asThinkpad
import util.Resource
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi

class ThinkrchiveRepositoryImpl(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val thinkpadDao: ThinkpadDao
) : ThinkrchiveRepository {
    private val logger = Logger.withTag("ThinkrchiveRepository")

    override suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<ThinkpadResponse>>> =
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

    override suspend fun refreshThinkpadList(response: List<ThinkpadResponse>) {
        thinkpadDao.insertAllThinkpads(response)
    }

    override suspend fun getAllThinkpads(): Flow<List<Thinkpad>> =
        thinkpadDao.getAllThinkpads().map {
            it.asDomainModel()
        }

    override suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>? =
        thinkpadDao.getThinkpad(thinkpadModel)?.map {
            it.asThinkpad()
        }

    override suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsAlphaAscending(thinkpadModel).map {
            it.asDomainModel()
        }

    override suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsNewestFirst(thinkpadModel).map {
            it.asDomainModel()
        }

    override suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsOldestFirst(thinkpadModel).map {
            it.asDomainModel()
        }

    override suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsLowPriceFirst(thinkpadModel).map {
            it.asDomainModel()
        }

    override suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsHighPriceFirst(thinkpadModel).map {
            it.asDomainModel()
        }
}