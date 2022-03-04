package work.racka.thinkrchive.v2.common.billing.api

import kotlinx.coroutines.flow.Flow

interface BillingApi {

    suspend fun loadOfferings(): Flow<List<Any>>

    fun purchase(item: Any): Flow<Any?>

    fun updatePermissions(): Flow<Any?>
}
