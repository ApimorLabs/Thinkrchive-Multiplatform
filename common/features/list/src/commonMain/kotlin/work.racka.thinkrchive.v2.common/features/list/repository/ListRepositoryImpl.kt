package work.racka.thinkrchive.v2.common.features.list.repository

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import data.remote.response.ThinkpadResponse
import domain.Thinkpad
import io.ktor.client.features.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import util.DataMappers.asDomainModel
import util.NetworkError
import util.Resource
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi

internal class ListRepositoryImpl(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val thinkpadDao: ThinkpadDao,
    private val backgroundDispatcher: CoroutineDispatcher
) : ListRepository {
    private val logger = Logger.apply {
        setLogWriters(CommonWriter())
        withTag("ListRepository")
    }

    override suspend fun getAllThinkpadsFromNetwork(): Flow<Resource<List<ThinkpadResponse>, NetworkError>> =
        withContext(backgroundDispatcher) {
            flow {
                logger.d { "getAllThinkpadsFromNetwork" }
                var replay = true
                emit(Resource.Loading())
                while (replay) {
                    val response = try {
                        val response = thinkrchiveApi.getThinkpads()
                        replay = false
                        Resource.Success<List<ThinkpadResponse>, NetworkError>(data = response)
                    } catch (e: ResponseException) {
                        logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                        replay = false
                        Resource.Error(
                            message = "Unknown or Limited Request: ${e.message}",
                            errorCode = NetworkError.StatusCodeError
                        )
                    } catch (e: SerializationException) {
                        logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                        replay = false
                        Resource.Error(
                            message = "Wrong Data Received: ${e.message}",
                            errorCode = NetworkError.SerializationError
                        )
                    } catch (e: Exception) {
                        logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                        replay = false
                        Resource.Error(
                            message = "No Network: ${e.message}",
                            errorCode = NetworkError.NoInternetError
                        )
                    }
                    emit(response)
                }
            }
        }

    override suspend fun refreshThinkpadList(response: List<ThinkpadResponse>) =
        withContext(backgroundDispatcher) {
            thinkpadDao.insertAllThinkpads(response)
        }

    override suspend fun getAllThinkpads(): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getAllThinkpads().map {
                it.asDomainModel()
            }
        }

    override suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpadsAlphaAscending(thinkpadModel).map {
                it.asDomainModel()
            }
        }

    override suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpadsNewestFirst(thinkpadModel).map {
                it.asDomainModel()
            }
        }

    override suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpadsOldestFirst(thinkpadModel).map {
                it.asDomainModel()
            }
        }

    override suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpadsLowPriceFirst(thinkpadModel).map {
                it.asDomainModel()
            }
        }

    override suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpadsHighPriceFirst(thinkpadModel).map {
                it.asDomainModel()
            }
        }
}