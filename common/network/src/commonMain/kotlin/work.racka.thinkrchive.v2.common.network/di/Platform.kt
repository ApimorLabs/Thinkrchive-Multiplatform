package work.racka.thinkrchive.v2.common.network.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformNetworkModule(): Module
}