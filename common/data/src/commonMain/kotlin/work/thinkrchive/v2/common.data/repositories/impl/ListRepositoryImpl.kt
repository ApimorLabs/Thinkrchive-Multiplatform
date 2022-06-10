package work.thinkrchive.v2.common.data.repositories.impl

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import data.remote.response.ThinkpadResponse
import domain.Thinkpad
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import util.NetworkError
import util.Resource
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

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
        flow {
            logger.d { "getAllThinkpadsFromNetwork" }
            emit(Resource.Loading())
            val response = try {
                val response = thinkrchiveApi.getThinkpads()
                Resource.Success<List<ThinkpadResponse>, NetworkError>(data = response)
            } catch (e: ResponseException) {
                logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                Resource.Error(
                    message = "Unknown or Limited Request: ${e.message}",
                    errorCode = NetworkError.StatusCodeError
                )
            } catch (e: SerializationException) {
                logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                Resource.Error(
                    message = "Wrong Data Received: ${e.message}",
                    errorCode = NetworkError.SerializationError
                )
            } catch (e: Exception) {
                logger.w(e) { "Exception during getAllThinkpadsFromNetwork: $e" }
                Resource.Error(
                    message = "No Network: ${e.message}",
                    errorCode = NetworkError.NoInternetError
                )
            }
            emit(response)
        }.flowOn(backgroundDispatcher)

    override suspend fun refreshThinkpadList(response: List<ThinkpadResponse>) =
        withContext(backgroundDispatcher) {
            thinkpadDao.insertAllThinkpads(response)
        }

    override suspend fun getAllThinkpads(): Flow<List<Thinkpad>> =
        thinkpadDao.getAllThinkpads().map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsAlphaAscending(thinkpadModel).map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsNewestFirst(thinkpadModel).map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsOldestFirst(thinkpadModel).map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsLowPriceFirst(thinkpadModel).map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<Thinkpad>> =
        thinkpadDao.getThinkpadsHighPriceFirst(thinkpadModel).map {
            it.asDomainModel()
        }.flowOn(backgroundDispatcher)

    override suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad?> =
        thinkpadDao.getThinkpad(thinkpadModel).map {
            it?.asThinkpad()
        }.flowOn(backgroundDispatcher)
}