package work.racka.thinkrchive.v2.desktop.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.RootComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.HomeSplitPaneScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootUI(rootComponent: RootComponent) {
    Children(
        routerState = rootComponent.routerState,
        animation = crossfadeScale(
            tween(300)
        )
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomePane -> HomeSplitPaneScreen(child.component)
            is RootComponent.Child.Settings -> SettingsScreen(child.component)
            is RootComponent.Child.About -> AboutScreen(child.component)
            is RootComponent.Child.Donate -> DonateScreen(child.component)
        }
    }
}