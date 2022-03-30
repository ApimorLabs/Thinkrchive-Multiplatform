package work.racka.thinkrchive.v2.android.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.theme.ThinkRchiveTheme

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ThinkrchiveApp(themeValue: Int = 0) {
    ThinkRchiveTheme(theme = themeValue) {
        Timber.d("ThinkrchiveApp called")
        val navController = rememberAnimatedNavController()
        Scaffold {
            ThinkrchiveNavHost(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}