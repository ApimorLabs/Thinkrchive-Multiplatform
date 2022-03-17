package work.racka.thinkrchive.v2.common.features.settings.di

import org.koin.core.KoinApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.common.features.thinkpad_details.di.CoroutineDispatchers
import work.racka.thinkrchive.v2.common.features.thinkpad_details.repository.DetailsRepository
import work.racka.thinkrchive.v2.common.features.thinkpad_details.repository.DetailsRepositoryImpl

object ThinkpadDetails {

    fun KoinApplication.appSettingsModules() =
        this.apply {
            modules(
                commonModule(),
                work.racka.thinkrchive.v2.common.features.thinkpad_details.di.Platform.platformDetailsModule()
            )
        }

    private fun commonModule() = module {

        single<DetailsRepository> {
            DetailsRepositoryImpl(
                thinkpadDao = get(),
                backgroundDispatcher = CoroutineDispatchers.backgroundDispatcher
            )
        }
    }
}