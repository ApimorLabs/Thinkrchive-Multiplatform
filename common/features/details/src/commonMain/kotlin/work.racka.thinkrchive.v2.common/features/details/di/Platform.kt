package work.racka.thinkrchive.v2.common.features.details.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformDetailsModule(): Module
}