package work.racka.thinkrchive.v2.android.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.koin.android.ext.android.inject
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.navigation.ThinkrchiveApp
import work.racka.thinkrchive.v2.common.integration.containers.settings.AppSettings

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {

    private val settings: AppSettings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Enable edge-to-edge experience and ProvideWindowInsets to the composable
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val state by settings.host.state.collectAsState()
            ThinkrchiveApp(state.themeValue)
            Timber.d("setContent called")
        }
    }
}
