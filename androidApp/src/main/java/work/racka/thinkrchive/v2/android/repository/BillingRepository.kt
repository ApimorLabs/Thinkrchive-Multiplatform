package work.racka.thinkrchive.v2.android.repository

import android.app.Activity
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsResult
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.collect
import work.racka.thinkrchive.v2.android.billing.BillingManager
import javax.inject.Inject

@ActivityScoped
class BillingRepository @Inject constructor(
    private val billingManager: BillingManager
) {
    val billingResponse = billingManager.billingResponse
    val purchasesState = billingManager.purchases

    suspend fun querySkuDetails(): SkuDetailsResult {
        val result = billingManager.querySkuDetails()
        billingManager.refreshPurchases()
        return result
    }

    fun launchPurchaseScreen(activity: Activity, skuDetails: SkuDetails) {
        billingManager.launchPurchaseScreen(activity, skuDetails)
    }

    suspend fun consumePurchase() {
        purchasesState.collect {
            billingManager.consumeConsumable(it)
        }
    }

    fun startConnection() = billingManager.startConnection()
    fun endConnection() = billingManager.endConnection()
}