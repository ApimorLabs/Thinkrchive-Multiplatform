package work.racka.thinkrchive.v2.android

import android.app.Application
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import timber.log.Timber
import work.racka.thinkrchive.v2.common.integration.di.KoinMain

class ThinkrchiveApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinMain.initKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ThinkrchiveApplication)
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