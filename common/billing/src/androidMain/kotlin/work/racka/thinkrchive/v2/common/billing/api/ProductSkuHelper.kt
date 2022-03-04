package work.racka.thinkrchive.v2.common.billing.api

import billing.ProductsOffered
import billing.Sku
import com.revenuecat.purchases.PackageType

fun PackageType.getSku(): Sku =
    when (this) {
        PackageType.UNKNOWN -> Sku.InAppPurchase
        PackageType.CUSTOM -> Sku.InAppPurchase
        PackageType.LIFETIME -> Sku.InAppPurchase
        else -> Sku.Subscription
    }

fun String.getProductOffered(): ProductsOffered? =
    when (this) {
        ProductsOffered.Premium.id -> ProductsOffered.Premium
        else -> null
    }