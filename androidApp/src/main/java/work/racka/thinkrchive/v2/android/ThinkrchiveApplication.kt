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
import states.settings.ThinkpadSettingsSideEffect
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.theme.Theme
import work.racka.thinkrchive.v2.common.integration.containers.settings.AppSettings
import work.racka.thinkrchive.v2.common.integration.di.KoinMain

class ThinkrchiveApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinMain.initKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ThinkrchiveApplication)
        }

        val settings by inject<AppSettings>()

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            settings.sideEffect.collectLatest { effect ->
                when (effect) {
                    is ThinkpadSettingsSideEffect.ApplyThemeOption -> {
                        if (effect.themeValue != Theme.MATERIAL_YOU.themeValue) {
                            AppCompatDelegate.setDefaultNightMode(effect.themeValue)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                        }
                    }
                    else -> {}
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