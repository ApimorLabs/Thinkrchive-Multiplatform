package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.router
import work.racka.thinkrchive.v2.desktop.ui.screens.HomeSplitPane
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsScreen

class NavHostComponent(
    private val componentContext: ComponentContext
) : Component, ComponentContext by componentContext {

    private val logger = Logger.withTag("NavHostComponent")

    private val router = router<Configuration, Component>(
        key = "MainRouter",
        initialConfiguration = Configuration.HomeSplitPane,
        childFactory = ::createScreenComponent
    )

    private fun createScreenComponent(
        config: Configuration,
        componentContext: ComponentContext
    ): Component {
        return when (config) {
            is Configuration.HomeSplitPane -> {
                logger.d { "HomeScreen" }
                HomeSplitPane(
                    componentContext = componentContext,
                    onAboutClicked = { NavigationActions.Home.onAboutClicked(router) },
                    onDonateClicked = { NavigationActions.Home.onDonateClicked(router) },
                    onSettingsClicked = { NavigationActions.Home.onSettingsClicked(router) }
                )
            }
            is Configuration.DonationScreen -> {
                DonateScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            is Configuration.ThinkpadAboutScreen -> {
                logger.d { "AboutScreen" }
                AboutScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            is Configuration.ThinkpadSettingsScreen -> {
                logger.d { "SettingsScreen" }
                SettingsScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            else -> {
                HomeSplitPane(
                    componentContext = componentContext,
                    onAboutClicked = { NavigationActions.Home.onAboutClicked(router) },
                    onDonateClicked = { NavigationActions.Home.onDonateClicked(router) },
                    onSettingsClicked = { NavigationActions.Home.onSettingsClicked(router) }
                )
            }
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val state = router.state.subscribeAsState()
        Children(
            routerState = router.state,
            animation = crossfadeScale(
                animationSpec = tween(300)
            )
        ) { child ->
            Column {
                Text(
                    text = state.value.activeChild.instance.toString(),
                    color = Color.White
                )
                logger.d { "Child rendered" }
                child.instance.render()
            }
        }
    }
}