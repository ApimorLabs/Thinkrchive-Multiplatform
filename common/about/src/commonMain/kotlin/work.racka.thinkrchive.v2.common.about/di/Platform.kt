package work.racka.thinkrchive.v2.common.about.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformAboutModule(): Module
}