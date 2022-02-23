package work.racka.thinkrchive.v2.android.di

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.data.local.dataStore.PrefDataStore

object DataModule {

    val dataStoreModule = module {
        single {
            PrefDataStore(
                context = androidApplication().applicationContext,
                dispatcher = Dispatchers.IO
            )
        }
    }
}