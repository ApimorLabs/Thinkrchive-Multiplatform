package work.racka.thinkrchive.v2.common.billing.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// TODO: Not yet implemented
class BillingApiImpl : BillingApi {
    override suspend fun loadOfferings(): Flow<List<Any>> {
        return flowOf(emptyList())
    }

    override fun purchase(item: Any): Flow<Any?> {
        return flowOf(null)
    }

    override fun updatePermissions(): Flow<Any?> {
        return flowOf(null)
    }
}