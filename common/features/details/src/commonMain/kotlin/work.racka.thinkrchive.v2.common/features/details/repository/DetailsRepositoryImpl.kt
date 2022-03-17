package work.racka.thinkrchive.v2.common.features.details.repository

import domain.Thinkpad
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import util.DataMappers.asThinkpad
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao

class DetailsRepositoryImpl(
    private val thinkpadDao: ThinkpadDao,
    private val backgroundDispatcher: CoroutineDispatcher
) : DetailsRepository {

    override suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad>? =
        withContext(backgroundDispatcher) {
            thinkpadDao.getThinkpad(thinkpadModel)?.map {
                it.asThinkpad()
            }
        }
}