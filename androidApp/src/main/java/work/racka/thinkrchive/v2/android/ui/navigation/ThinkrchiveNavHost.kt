package work.racka.thinkrchive.v2.android.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.ui.main.screens.about.aboutScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.details.thinkpadDetailsScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.donate.donateScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.list.thinkpadListScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.settings.settingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = ThinkrchiveScreens.ThinkpadListScreen.name,
        route = "MainNavHost"
    ) {
        Timber.d("thinkpadNavHost called")

        // Main List Screen
        thinkpadListScreen(modifier = modifier, navController = navController)

        // Details Screen
        thinkpadDetailsScreen(modifier = modifier, navController = navController)

        // Settings Screen
        settingsScreen(navController = navController)

        // About Screen
        aboutScreen(navController = navController)

        // Donate Screen
        donateScreen(navController = navController)
    }
}
