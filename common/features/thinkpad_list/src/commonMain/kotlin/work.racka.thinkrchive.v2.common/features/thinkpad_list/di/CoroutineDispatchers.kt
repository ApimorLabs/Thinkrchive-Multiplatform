package work.racka.thinkrchive.v2.common.features.thinkpad_list.di

import kotlinx.coroutines.CoroutineDispatcher

internal expect object CoroutineDispatchers {
    val backgroundDispatcher: CoroutineDispatcher
}