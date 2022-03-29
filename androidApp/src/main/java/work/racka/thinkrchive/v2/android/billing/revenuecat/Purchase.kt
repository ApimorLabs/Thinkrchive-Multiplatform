package work.racka.thinkrchive.v2.android.billing.revenuecat

import android.app.Activity
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.purchasePackageWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import util.Resource

class Purchase {

    private val _result: MutableStateFlow<Resource<CustomerInfo, Any>> =
        MutableStateFlow(Resource.Loading())
    val result: StateFlow<Resource<CustomerInfo, Any>>
        get() = _result

    // This should only be called when initiating a purchase
    fun initiatePurchase(
        activity: Activity,
        item: Package
    ) {
        Purchases.sharedInstance.purchasePackageWith(
            activity = activity,
            packageToPurchase = item,
            onError = { error, userCancelled ->
                _result.value = Resource.Error(message = "Error: ${error.message}")
            },
            onSuccess = { product, customerInfo ->
                _result.value = Resource.Success(
                    data = customerInfo
                )
            }
        )
    }
}