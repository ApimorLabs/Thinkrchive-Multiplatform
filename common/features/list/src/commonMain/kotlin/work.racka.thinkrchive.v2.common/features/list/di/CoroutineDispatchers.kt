package work.racka.thinkrchive.v2.common.features.list.di

import kotlinx.coroutines.CoroutineDispatcher

internal expect object CoroutineDispatchers {
    val backgroundDispatcher: CoroutineDispatcher
}