package work.racka.thinkrchive.v2.common.database.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApi
import work.racka.thinkrchive.v2.common.database.remote.ThinkrchiveApiImpl
import work.racka.thinkrchive.v2.common.database.repository.ThinkrchiveRepository
import work.racka.thinkrchive.v2.common.database.repository.ThinkrchiveRepositoryImpl

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

        single {
            CoroutineScope(Dispatchers.Default + SupervisorJob())
        }

        single<ThinkrchiveRepository> {
            ThinkrchiveRepositoryImpl(
                thinkrchiveApi = get(),
                thinkpadDatabase = get(),
                coroutineScope = get()
            )
        }
    }
}