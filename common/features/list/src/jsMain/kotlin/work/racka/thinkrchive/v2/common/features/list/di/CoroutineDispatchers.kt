package work.racka.thinkrchive.v2.common.features.list.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual object CoroutineDispatchers {
    actual val backgroundDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
}