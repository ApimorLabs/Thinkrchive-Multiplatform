package work.racka.thinkrchive.v2.common.billing.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformBillingModule(): Module
}