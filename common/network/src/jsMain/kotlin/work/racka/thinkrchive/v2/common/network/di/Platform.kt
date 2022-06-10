package work.racka.thinkrchive.v2.common.network.di

import io.ktor.client.engine.js.*
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual object Platform {
    actual fun platformNetworkModule(): Module = module {
        factory { Js.create() }
    }
}