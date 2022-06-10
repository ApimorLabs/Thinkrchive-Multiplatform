package work.racka.thinkrchive.v2.common.network.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApiImpl


object Network {

    fun KoinApplication.networkModules(enableNetworkLogs: Boolean = false) =
        this.apply {
            modules(
                commonModule(enableNetworkLogs),
                Platform.platformNetworkModule()
            )
        }

    private fun commonModule(enableNetworkLogs: Boolean) = module {
        factory {
            HttpClient(
                engine = get()
            ) {

                install(ContentNegotiation) { json() }

                if (enableNetworkLogs) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.INFO
                    }
                }
            }
        }

        factory<ThinkrchiveApi> { ThinkrchiveApiImpl(get()) }
    }
}