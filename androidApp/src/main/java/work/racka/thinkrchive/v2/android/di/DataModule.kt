package work.racka.thinkrchive.v2.android.di

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import work.racka.thinkrchive.v2.android.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.v2.android.data.local.database.ThinkpadDatabase
import work.racka.thinkrchive.v2.android.repository.ThinkpadRepository
import work.racka.thinkrchive.v2.android.utils.Constants.THINKPAD_LIST_TABLE

object DataModule {

    val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidApplication().applicationContext,
                ThinkpadDatabase::class.java,
                THINKPAD_LIST_TABLE
            ).fallbackToDestructiveMigration()
                .build()
        }

        single {
            val database: ThinkpadDatabase = get()
            database.thinkpadDao
        }
    }

    val dataStoreModule = module {
        single {
            PrefDataStore(
                context = androidApplication().applicationContext,
                dispatcher = Dispatchers.IO
            )
        }
    }

    val repositoryModule = module {
        single {
            ThinkpadRepository(
                thinkrchiveApi = get(),
                thinkpadDao = get(),
                dispatcher = Dispatchers.IO
            )
        }
    }
}