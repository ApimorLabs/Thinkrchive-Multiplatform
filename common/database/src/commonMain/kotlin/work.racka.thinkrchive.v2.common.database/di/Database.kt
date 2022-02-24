package work.racka.thinkrchive.v2.common.database.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.database.repository.ThinkrchiveRepository
import work.racka.thinkrchive.v2.common.database.repository.ThinkrchiveRepositoryImpl

object Database {

    fun KoinApplication.databaseModules() =
        this.apply {
            modules(
                commonModule(),
                platformDatabaseModule()
            )
        }

    private fun commonModule() = module {

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