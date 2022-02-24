package work.racka.thinkrchive.v2.common.network.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApi
import work.racka.thinkrchive.v2.common.network.remote.ThinkrchiveApiImpl


object Network {

    fun KoinApplication.networkModules(enableNetworkLogs: Boolean = false) =
        this.apply {
            modules(
                commonModule(enableNetworkLogs),
                platformNetworkModule()
            )
        }

    private fun commonModule(enableNetworkLogs: Boolean) = module {
        single {
            HttpClient(get()) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }

                if (enableNetworkLogs) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.INFO
                    }
                }
            }
        }

        single<ThinkrchiveApi> { ThinkrchiveApiImpl(get()) }
    }
}