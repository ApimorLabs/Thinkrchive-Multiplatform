package work.racka.thinkrchive.v2.common.features.settings.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformSettingsModule(): Module
}