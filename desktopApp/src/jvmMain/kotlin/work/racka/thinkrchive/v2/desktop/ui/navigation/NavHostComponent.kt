package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.router.router
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.details.ThinkpadDetailsScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.list.ThinkpadListScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsScreen

class NavHostComponent(
    private val componentContext: ComponentContext
) : Component, ComponentContext by componentContext {

    private val router = router<Configuration, Component>(
        initialConfiguration = Configuration.ThinkpadListScreen,
        childFactory = ::createScreenComponent
    )

    private fun createScreenComponent(
        config: Configuration,
        componentContext: ComponentContext
    ): Component {
        return when (config) {
            is Configuration.ThinkpadListScreen -> {
                ThinkpadListScreen(
                    componentContext = componentContext,
                    onEntryClick = { model ->
                        NavigationActions.List.onEntryClick(router, model)
                    },
                    onAboutClicked = { NavigationActions.List.onAboutClicked(router) },
                    onDonateClicked = { NavigationActions.List.onDonateClicked(router) },
                    onSettingsClicked = { NavigationActions.List.onSettingsClicked(router) }
                )
            }
            is Configuration.ThinkpadDetailsScreen -> {
                ThinkpadDetailsScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) },
                    model = config.model
                )
            }
            is Configuration.DonationScreen -> {
                DonateScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            is Configuration.ThinkpadAboutScreen -> {
                AboutScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            is Configuration.ThinkpadSettingsScreen -> {
                SettingsScreen(
                    componentContext = componentContext,
                    onBackClicked = { NavigationActions.goBack(router) }
                )
            }
            else -> {
                ThinkpadListScreen(
                    componentContext = componentContext,
                    onEntryClick = { model ->
                        NavigationActions.List.onEntryClick(router, model)
                    },
                    onAboutClicked = { NavigationActions.List.onAboutClicked(router) },
                    onDonateClicked = { NavigationActions.List.onDonateClicked(router) },
                    onSettingsClicked = { NavigationActions.List.onSettingsClicked(router) }
                )
            }
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        Children(
            routerState = router.state,
            animation = crossfadeScale(
                animationSpec = tween(300)
            )
        ) { child ->
            child.instance.render()
        }
    }
}