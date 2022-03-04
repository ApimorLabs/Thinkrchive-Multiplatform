package work.racka.thinkrchive.v2.common.integration.containers.donate

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import domain.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.billing.api.getProductOffered
import work.racka.thinkrchive.v2.common.billing.api.getSku
import work.racka.thinkrchive.v2.common.integration.util.Constants

actual class DonateContainerHost(
    private val api: BillingApi,
    scope: CoroutineScope
) : ContainerHost<DonateState.State, DonateSideEffect> {
    override val container: Container<DonateState.State, DonateSideEffect> =
        scope.container(DonateState.EmptyState) {
            loadOfferings()
            updatePermissions()
        }

    private val packages: MutableStateFlow<List<Package>> = MutableStateFlow(listOf())

    // It will always return a list of Package
    // If it doesn't then we have bigger problems than this
    @Suppress("unchecked_cast")
    private fun loadOfferings() = intent {
        //packages.value = api.loadOfferings() as List<Package>
        try {
            api.loadOfferings().collectLatest { list ->
                packages.value = list as List<Package>
                reduce {
                    state.copy(
                        product = packages.value.map {
                            it.identifier
                            Product(
                                title = it.product.title,
                                description = it.product.description,
                                price = it.product.price,
                                identifier = it.identifier,
                                type = it.packageType.getSku()
                            )
                        }
                    )
                }
            }
        } catch (e: IllegalStateException) {
            postSideEffect(DonateSideEffect.Error(e.message ?: "Purchase Error"))
        } catch (e: Exception) {
            postSideEffect(DonateSideEffect.Error(e.message ?: "Unknown Error"))
        }
    }

    fun purchase(index: Int) = intent {
        val item = packages.value[index]

        // Should be cast to Package as it will always be that
        postSideEffect(
            DonateSideEffect.LaunchPurchaseFlow(item)
        )
    }

    fun processPurchase(success: Boolean, errorMsg: String = "") = intent {
        if (success) {
            updatePermissions()
        } else {
            postSideEffect(
                DonateSideEffect.Error(errorMsg)
            )
        }
    }

    private fun savePurchase(product: Product) = intent {
        postSideEffect(
            DonateSideEffect.ShowPurchaseSuccessToast(
                "${Constants.PURCHASE_SUCCESS_APPEND} ${product.accessTo?.name}"
            )
        )
//        postSideEffect(
//            DonateSideEffect.ProductAuthorized(product)
//        )
    }

    private fun updatePermissions() = intent {
        try {
            api.updatePermissions().collectLatest { result ->
                val customerInfo = result as CustomerInfo?
                val newProductList = state.product.map { product ->
                    customerInfo?.entitlements?.get(product.identifier)?.let {
                        if (it.isActive) {
                            product.accessTo = product.identifier.getProductOffered()
                            savePurchase(product)
                        }
                    }
                    product
                }
                reduce {
                    state.copy(
                        product = newProductList
                    )
                }
            }
        } catch (e: IllegalStateException) {
            postSideEffect(
                DonateSideEffect.Error(errorMsg = e.message!!)
            )
        } catch (e: Exception) {
            postSideEffect(
                DonateSideEffect
                    .Error(errorMsg = e.message ?: "Unknown Error")
            )
        }
    }

    fun detachSideEffect() = intent {
        postSideEffect(DonateSideEffect.Nothing)
    }
}