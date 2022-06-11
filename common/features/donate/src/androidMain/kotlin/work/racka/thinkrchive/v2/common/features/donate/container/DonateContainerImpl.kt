package work.racka.thinkrchive.v2.common.features.donate.container

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import domain.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.billing.api.getProductOffered
import work.racka.thinkrchive.v2.common.billing.api.getSku
import work.racka.thinkrchive.v2.common.features.donate.util.Constants

internal class DonateContainerImpl(
    private val api: BillingApi,
    private val scope: CoroutineScope
) : DonateContainer {

    private val _state: MutableStateFlow<DonateState.State> =
        MutableStateFlow(DonateState.EmptyState)
    override val state: StateFlow<DonateState.State>
        get() = _state

    private val _sideEffect = Channel<DonateSideEffect>(capacity = Channel.UNLIMITED)
    override val sideEffect: Flow<DonateSideEffect>
        get() = _sideEffect.receiveAsFlow()

    private val packages: MutableStateFlow<List<Package>> = MutableStateFlow(listOf())

    init {
        loadOfferings()
        updatePermissions()
    }

    // It will always return a list of Package
    // If it doesn't then we have bigger problems than this
    @Suppress("unchecked_cast")
    private fun loadOfferings() {
        //packages.value = api.loadOfferings() as List<Package>
        scope.launch {
            try {
                api.loadOfferings().collectLatest { list ->
                    packages.value = list as List<Package>
                    val mappedProducts = packages.value.map {
                        Product(
                            title = it.product.title,
                            description = it.product.description,
                            price = it.product.price,
                            identifier = it.identifier,
                            type = it.packageType.getSku()
                        )
                    }
                    _state.update { oldState ->
                        oldState.copy(
                            product = mappedProducts
                        )
                    }
                }
            } catch (e: IllegalStateException) {
                _sideEffect.send(DonateSideEffect.Error(e.message ?: "Purchase Error"))
            } catch (e: Exception) {
                _sideEffect.send(DonateSideEffect.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun purchase(index: Int) {
        val item = packages.value[index]

        // Should be cast to Package as it will always be that
        scope.launch {
            _sideEffect.send(
                DonateSideEffect.LaunchPurchaseFlow(item)
            )
        }
    }

    override fun processPurchase(success: Boolean, errorMsg: String) {
        if (success) {
            updatePermissions()
        } else {
            scope.launch {
                _sideEffect.send(
                    DonateSideEffect.Error(errorMsg)
                )
            }
        }
    }

    private fun savePurchase(product: Product) {
        scope.launch {
            _sideEffect.send(
                DonateSideEffect.ShowPurchaseSuccessToast(
                    "${Constants.PURCHASE_SUCCESS_APPEND} ${product.accessTo?.name}"
                )
            )
        }
//        postSideEffect(
//            DonateSideEffect.ProductAuthorized(product)
//        )
    }

    private fun updatePermissions() {
        scope.launch {
            try {
                api.updatePermissions().collectLatest { result ->
                    val customerInfo = result as CustomerInfo?
                    val newProductList = state.value.product.map { product ->
                        customerInfo?.entitlements?.get(product.identifier)?.let {
                            if (it.isActive) {
                                product.accessTo = product.identifier.getProductOffered()
                                savePurchase(product)
                            }
                        }
                        product
                    }
                    _state.update {
                        it.copy(
                            product = newProductList
                        )
                    }
                }
            } catch (e: IllegalStateException) {
                _sideEffect.send(
                    DonateSideEffect.Error(errorMsg = e.message!!)
                )
            } catch (e: Exception) {
                _sideEffect.send(
                    DonateSideEffect
                        .Error(errorMsg = e.message ?: "Unknown Error")
                )
            }
        }
    }
}