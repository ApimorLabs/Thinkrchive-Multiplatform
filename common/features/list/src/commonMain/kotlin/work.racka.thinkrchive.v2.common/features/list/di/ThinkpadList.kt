package work.racka.thinkrchive.v2.common.features.list.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.list.container.ThinkpadListHelper
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepository
import work.racka.thinkrchive.v2.common.features.list.repository.ListRepositoryImpl

object ThinkpadList {

    fun KoinApplication.listModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformSettingsModule()
            )
        }

    private fun commonModule() = module {

        factory<ListRepository> {
            ListRepositoryImpl(
                thinkrchiveApi = get(),
                thinkpadDao = get(),
                backgroundDispatcher = CoroutineDispatchers.backgroundDispatcher
            )
        }

        factory {
            ThinkpadListHelper(
                repository = get()
            )
        }
    }
}