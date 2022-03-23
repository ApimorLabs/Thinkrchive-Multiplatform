package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.HomePaneComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.HomeSplitPaneScreen

interface RootComponent {
    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {
        data class HomePane(val component: HomePaneComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
        data class About(val component: AboutComponent) : Child()
        data class Donate(val component: DonateComponent) : Child()
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootUI(rootComponent: RootComponent) {
    val logger = Logger.withTag("RootComponent")
    Children(
        routerState = rootComponent.routerState
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomePane -> {
                logger.d { "Home Child created" }
                HomeSplitPaneScreen(child.component)
            }
            is RootComponent.Child.Settings -> {
                logger.d { "Settings Child Created" }
                SettingsScreen(child.component)
            }
            is RootComponent.Child.About -> {

                AboutScreen(child.component)
            }
            is RootComponent.Child.Donate -> DonateScreen(child.component)
        }
    }
}