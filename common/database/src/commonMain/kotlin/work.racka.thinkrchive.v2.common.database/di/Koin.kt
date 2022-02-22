package work.racka.thinkrchive.v2.common.database.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApi
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApiImpl

object Koin {

    fun initKoin(
        enableNetworkLogs: Boolean = false,
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()
        modules()
    }

    fun commonModule(enableNetworkLogs: Boolean) = module {
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