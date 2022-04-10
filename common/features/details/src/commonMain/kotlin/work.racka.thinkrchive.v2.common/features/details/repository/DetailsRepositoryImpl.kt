package work.racka.thinkrchive.v2.common.features.details.repository

import domain.Thinkpad
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import util.DataMappers.asThinkpad
import work.racka.thinkrchive.v2.common.database.dao.ThinkpadDao

internal class DetailsRepositoryImpl(
    private val thinkpadDao: ThinkpadDao,
    private val backgroundDispatcher: CoroutineDispatcher
) : DetailsRepository {

    override suspend fun getThinkpad(thinkpadModel: String): Flow<Thinkpad?> =
        thinkpadDao.getThinkpad(thinkpadModel).map {
            it?.asThinkpad()
        }.flowOn(backgroundDispatcher)
}