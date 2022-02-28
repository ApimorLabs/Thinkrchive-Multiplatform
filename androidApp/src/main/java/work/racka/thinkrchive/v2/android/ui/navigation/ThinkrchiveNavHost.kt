package work.racka.thinkrchive.v2.android.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.main.screens.ThinkrchiveScreens
import work.racka.thinkrchive.v2.android.ui.main.screens.about.AboutScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.details.ThinkpadDetailsScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.list.ThinkpadListScreen
import work.racka.thinkrchive.v2.android.ui.main.screens.settings.SettingsScreen

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
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
        ThinkpadListScreen(modifier = modifier, navController = navController)

        // Details Screen
        ThinkpadDetailsScreen(modifier = modifier, navController = navController)

        // Settings Screen
        SettingsScreen(navController = navController)

        // About Screen
        AboutScreen(navController = navController)

        // Donate Screen
        DonateScreen(navController = navController)
    }
}
