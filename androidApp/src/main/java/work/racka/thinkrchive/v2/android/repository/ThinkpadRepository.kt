package work.racka.thinkrchive.v2.android.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import work.racka.thinkrchive.v2.android.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.v2.android.data.local.database.ThinkpadDao
import work.racka.thinkrchive.v2.android.data.local.database.ThinkpadDatabaseObject
import work.racka.thinkrchive.v2.android.data.remote.api.ThinkrchiveApi
import work.racka.thinkrchive.v2.android.data.remote.responses.ThinkpadResponse

class ThinkpadRepository(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val thinkpadDao: ThinkpadDao,
    private val dispatcher: CoroutineDispatcher
) {

    // Get all the Thinkpads from the network
    suspend fun getAllThinkpadsFromNetwork(): List<ThinkpadResponse> =
        thinkrchiveApi.getThinkpads()

    // Get all Thinkpads from the Database
    fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getAllThinkpads()
    }

    // Get requested Thinkpads from the Database
    // This has been replaced by the sorting specific functions below
    fun queryThinkpads(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.searchDatabase("%$query%")
    }

    fun getThinkpadsAlphaAscending(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsAlphaAscending("%$query%")
    }

    fun getThinkpadsNewestFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsNewestFirst("%$query%")
    }

    fun getThinkpadsOldestFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsOldestFirst("%$query%")
    }

    fun getThinkpadsLowPriceFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsLowPriceFirst("%$query%")
    }

    fun getThinkpadsHighPriceFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsHighPriceFirst("%$query%")
    }

    // Insert all Thinkpads obtained from the network to the database
    suspend fun refreshThinkpadList(thinkpadList: List<ThinkpadResponse>) {
        withContext(dispatcher) {
            thinkpadDao.insertAllThinkpads(*thinkpadList.asDatabaseModel())
        }
    }

    // Get a single Thinkpad entry from the DB
    fun getThinkpad(thinkpad: String): Flow<ThinkpadDatabaseObject> {
        return thinkpadDao.getThinkpad(thinkpad = thinkpad)
    }
}