package work.racka.thinkrchive.v2.common.features.list.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformListModule(): Module
}