package work.racka.thinkrchive.v2.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.theme.Theme
import work.racka.thinkrchive.v2.common.integration.di.KoinMain
import work.racka.thinkrchive.v2.common.settings.repository.MultiplatformSettings

class ThinkrchiveApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinMain.initKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ThinkrchiveApplication)
        }

        val settings by inject<MultiplatformSettings>()

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            settings.themeFlow.collectLatest { themeValue ->
                if (themeValue != Theme.MATERIAL_YOU.themeValue) {
                    AppCompatDelegate.setDefaultNightMode(themeValue)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
        Timber.plant(Timber.DebugTree())

        // Revenuecat
        Purchases.debugLogsEnabled = true
        Purchases.configure(
            configuration = PurchasesConfiguration.Builder(
                context = this,
                BuildConfig.revenuecat_key
            ).build()
        )
    }
}