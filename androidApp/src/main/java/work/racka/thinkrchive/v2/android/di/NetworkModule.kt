package work.racka.thinkrchive.v2.android.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.data.remote.api.ThinkrchiveApi
import work.racka.thinkrchive.v2.android.data.remote.api.ThinkrchiveApiImpl

object NetworkModule {

    val module = module {
        single<ThinkrchiveApi> {
            ThinkrchiveApiImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}
