package work.racka.thinkrchive.v2.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.qonversion.android.sdk.Qonversion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import timber.log.Timber
import work.racka.thinkrchive.v2.android.data.local.dataStore.PrefDataStore
import work.racka.thinkrchive.v2.android.di.BillingModule
import work.racka.thinkrchive.v2.android.di.DataModule
import work.racka.thinkrchive.v2.android.di.ViewModelModule
import work.racka.thinkrchive.v2.common.integration.di.KoinMain

class ThinkrchiveApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinMain.initKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ThinkrchiveApplication)
            modules(
                DataModule.dataStoreModule(),
                BillingModule.module(),
                ViewModelModule.module()
            )
        }

        val prefDataStore by inject<PrefDataStore>()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            prefDataStore.readThemeSetting.collect {
                if (it != 12) {
                    AppCompatDelegate.setDefaultNightMode(it)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
        Timber.plant(Timber.DebugTree())
        Qonversion.setDebugMode()
        Qonversion.launch(
            context = this,
            key = BuildConfig.qonversion_key,
            observeMode = false
        )
    }
}