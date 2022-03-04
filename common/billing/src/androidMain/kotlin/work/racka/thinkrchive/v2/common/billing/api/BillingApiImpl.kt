package work.racka.thinkrchive.v2.common.billing.api

import com.revenuecat.purchases.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf

// Need a better way to call purchase screen without including
// Activity instances in API implementation

internal class BillingApiImpl(
    private val purchases: Purchases
) : BillingApi {

    // Should be cast to List<Package> on Android side
    override suspend fun loadOfferings(): Flow<List<Any>> = callbackFlow {
        purchases.getOfferingsWith(
            onError = { error ->
                val cause = Throwable(message = error.message)
                channel.close(IllegalStateException(cause))
                //throw IllegalStateException(exception)
            },
            onSuccess = { result ->
                val offerings = result.current
                    ?.availablePackages ?: listOf()
                if (trySend(offerings).isSuccess) channel.close()
            }
        )
        awaitClose { purchases.syncPurchases() }
    }

    // Use RevcatPurchase.kt in :androidApp to prevent leaking
    // Activities in business logic
    override fun purchase(item: Any): Flow<Any?> {
        return flowOf(null)
    }

    // Should be cast to CustomerInfo on Android side
    override fun updatePermissions(): Flow<Any?> = callbackFlow {
        purchases.getCustomerInfoWith(
            onError = { error: PurchasesError ->
                val cause = Throwable(message = error.message)
                channel.close(IllegalStateException(cause))
                //throw IllegalStateException(exception)
            },
            onSuccess = { customerInfo: CustomerInfo ->
                if (trySend(customerInfo).isSuccess) channel.close()
            }
        )
        awaitClose { }
    }
}