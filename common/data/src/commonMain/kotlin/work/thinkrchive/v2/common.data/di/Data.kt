package work.thinkrchive.v2.common.data.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.thinkrchive.v2.common.data.repositories.helpers.ListRepositoryHelper
import work.thinkrchive.v2.common.data.repositories.impl.ListRepositoryImpl
import work.thinkrchive.v2.common.data.repositories.interfaces.ListRepository

object Data {
    fun KoinApplication.dataModules() = run {
        modules(
            commonModule()
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
            ListRepositoryHelper(repository = get())
        }
    }
}