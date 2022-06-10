package work.racka.thinkrchive.v2.common.database.dao

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import data.local.ThinkpadDatabaseObject
import data.remote.response.ThinkpadResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import work.racka.thinkrchive.v2.common.database.ThinkrchiveDatabaseProvider
import work.racka.thinkrchive.v2.common.database.util.Helpers.getAllThinkpadsFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsAlphaAscendingFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsHighPriceFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsLowPriceFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsNewestFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.getThinkpadsOldestFromDb
import work.racka.thinkrchive.v2.common.database.util.Helpers.insertAllThinkpadsToDb

internal class ThinkpadDaoImpl(
    private val coroutineScope: CoroutineScope = MainScope(),
    private val dbProvider: ThinkrchiveDatabaseProvider,
) : ThinkpadDao {

    override suspend fun insertAllThinkpads(response: List<ThinkpadResponse>) = dbProvider { db ->
        db.thinkpadDatabaseQueries.insertAllThinkpadsToDb(response)
    }

    override suspend fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>> = dbProvider { db ->
        db.thinkpadDatabaseQueries.getAllThinkpadsFromDb()
            .asFlow().mapToList()
    }

    override suspend fun getThinkpad(thinkpadModel: String): Flow<ThinkpadDatabaseObject?> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadFromDb(thinkpadModel)
                .asFlow()
                .mapToOne(context = coroutineScope.coroutineContext)
        }

    override suspend fun getThinkpadsAlphaAscending(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadsAlphaAscendingFromDb("%$thinkpadModel%")
                .asFlow().mapToList()
        }

    override suspend fun getThinkpadsNewestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadsNewestFromDb("%$thinkpadModel%")
                .asFlow().mapToList()
        }

    override suspend fun getThinkpadsOldestFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadsOldestFromDb("%$thinkpadModel%")
                .asFlow().mapToList()
        }

    override suspend fun getThinkpadsLowPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadsLowPriceFromDb("%$thinkpadModel%")
                .asFlow().mapToList()
        }

    override suspend fun getThinkpadsHighPriceFirst(thinkpadModel: String): Flow<List<ThinkpadDatabaseObject>> =
        dbProvider { db ->
            db.thinkpadDatabaseQueries.getThinkpadsHighPriceFromDb("%$thinkpadModel%")
                .asFlow().mapToList()
        }
}
