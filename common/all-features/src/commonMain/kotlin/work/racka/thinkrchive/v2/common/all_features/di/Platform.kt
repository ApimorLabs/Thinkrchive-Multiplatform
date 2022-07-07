package work.racka.thinkrchive.v2.common.all_features.di

import org.koin.core.module.Module

internal expect object Platform {
    fun platformFeaturesModule(): Module
}