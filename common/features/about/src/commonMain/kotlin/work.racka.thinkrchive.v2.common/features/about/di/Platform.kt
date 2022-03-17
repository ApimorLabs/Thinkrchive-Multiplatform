package work.racka.thinkrchive.v2.common.features.about.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformAboutModule(): Module
}