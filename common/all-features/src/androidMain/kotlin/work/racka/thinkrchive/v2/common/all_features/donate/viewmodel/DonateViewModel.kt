package work.racka.thinkrchive.v2.common.all_features.donate.viewmodel

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import domain.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import states.donate.DonateSideEffect
import states.donate.DonateState
import work.racka.common.mvvm.viewmodel.CommonViewModel
import work.racka.thinkrchive.v2.common.all_features.util.Constants
import work.racka.thinkrchive.v2.common.billing.api.BillingApi
import work.racka.thinkrchive.v2.common.billing.api.getProductOffered
import work.racka.thinkrchive.v2.common.billing.api.getSku

actual class DonateViewModel(
    private val api: BillingApi
) : CommonViewModel() {

    private val _state: MutableStateFlow<DonateState.State> =
        MutableStateFlow(DonateState.EmptyState)
    actual val state: StateFlow<DonateState.State>
        get() = _state

    private val _sideEffect = Channel<DonateSideEffect>(capacity = Channel.UNLIMITED)
    actual val sideEffect: Flow<DonateSideEffect>
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
        vmScope.launch {
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

    actual fun purchase(index: Int) {
        val item = packages.value[index]

        // Should be cast to Package as it will always be that
        vmScope.launch {
            _sideEffect.send(
                DonateSideEffect.LaunchPurchaseFlow(item)
            )
        }
    }

    actual fun processPurchase(success: Boolean, errorMsg: String) {
        if (success) {
            updatePermissions()
        } else {
            vmScope.launch {
                _sideEffect.send(
                    DonateSideEffect.Error(errorMsg)
                )
            }
        }
    }

    private fun savePurchase(product: Product) {
        vmScope.launch {
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
        vmScope.launch {
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