package work.racka.thinkrchive.v2.common.features.details.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.details.repository.DetailsRepository
import work.racka.thinkrchive.v2.common.features.details.repository.DetailsRepositoryImpl

object ThinkpadDetails {

    fun KoinApplication.detailsModules() =
        this.apply {
            modules(
                commonModule(),
                Platform.platformDetailsModule()
            )
        }

    private fun commonModule() = module {

        factory<DetailsRepository> {
            DetailsRepositoryImpl(
                thinkpadDao = get(),
                backgroundDispatcher = CoroutineDispatchers.backgroundDispatcher
            )
        }
    }
}