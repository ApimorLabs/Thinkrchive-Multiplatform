package work.thinkrchive.v2.common.data.di

import kotlinx.coroutines.CoroutineDispatcher

internal expect object CoroutineDispatchers {
    val backgroundDispatcher: CoroutineDispatcher
}