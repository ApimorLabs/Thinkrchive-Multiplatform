package work.racka.thinkrchive.v2.common.database.dao

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import data.local.ThinkpadDatabaseObject
import data.remote.response.ThinkpadResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import work.racka.thinkrchive.v2.common.database.util.Helpers.getAllThinkpadsFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsAlphaAscendingFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsHighPriceFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsLowPriceFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsNewestFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsOldestFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.insertAllThinkpadsToDb

class ThinkpadDaoImpl(
    private val coroutineScope: CoroutineScope = MainScope(),
    thinkpadDatabaseWrapper: ThinkrchiveDatabaseWrapper
) : ThinkpadDao {
    private val thinkpadDatabaseQueries = thinkpadDatabaseWrapper
        .instance?.thinkpadDatabaseQueries

    override fun insertAllThinkpads(response: List<ThinkpadResponse>) {
        thinkpadDatabaseQueries?.insertAllThinkpadsToDb(response)
    }

    override fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getAllThinkpadsFromDb()
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override fun getThinkpad(thinkpadModel: String): Flow<ThinkpadDatabaseObject>? {
        return thinkpadDatabaseQueries?.getThinkpadFromDb(thinkpadModel)
            ?.asFlow()
            ?.mapToOne(context = coroutineScope.coroutineContext)
    }

    override fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getThinkpadsAlphaAscendingFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getThinkpadsNewestFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getThinkpadsOldestFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getThinkpadsLowPriceFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        thinkpadDatabaseQueries?.getThinkpadsHighPriceFromDb("%$thinkpadModel%")
            ?.asFlow()?.mapToList() ?: flowOf(emptyList())
}