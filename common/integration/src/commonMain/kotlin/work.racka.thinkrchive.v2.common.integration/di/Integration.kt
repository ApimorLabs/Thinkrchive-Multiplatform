package work.racka.thinkrchive.v2.common.integration.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepository
import work.racka.thinkrchive.v2.common.integration.repository.ThinkrchiveRepositoryImpl


internal object Integration {

    fun KoinApplication.integrationModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformIntegrationModule()
            )
        }

    private fun commonModule() = module {
        single {
            CoroutineScope(Dispatchers.Default + SupervisorJob())
        }

        single<ThinkrchiveRepository> {
            ThinkrchiveRepositoryImpl(
                thinkrchiveApi = get(),
                thinkpadDao = get()
            )
        }

    }
}