package work.racka.thinkrchive.v2.common.network.di

import io.ktor.client.engine.java.*
import org.koin.dsl.module

internal actual fun platformNetworkModule() = module {
    single { Java.create() }
}