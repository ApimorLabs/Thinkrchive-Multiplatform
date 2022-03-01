package work.racka.thinkrchive.v2.common.network.di

import io.ktor.client.engine.android.*
import org.koin.dsl.module


internal actual fun platformNetworkModule() = module {
    single { Android.create() }
}