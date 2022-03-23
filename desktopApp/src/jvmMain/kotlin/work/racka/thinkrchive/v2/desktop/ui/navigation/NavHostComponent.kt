package work.racka.thinkrchive.v2.desktop.ui.navigation

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutComponentImpl
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateComponentImpl
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsComponentImpl
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.HomePaneComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.HomePaneComponentImpl

class NavHostComponent(
    private val componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val logger = Logger.withTag("NavHostComponent")

    private val router = router<Configuration, RootComponent.Child>(
        key = "MainRouter",
        initialConfiguration = Configuration.HomeSplitPane,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<Configuration, RootComponent.Child>>
        get() = router.state

    private fun createChild(
        config: Configuration,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Configuration.HomeSplitPane -> {
                logger.d { "HomeScreen" }
                RootComponent.Child.HomePane(homePaneComponent(componentContext))
            }
            is Configuration.DonationScreen -> {
                logger.d { "DonateScreen" }
                RootComponent.Child.Donate(donateComponent(componentContext))
            }
            is Configuration.ThinkpadAboutScreen -> {
                logger.d { "AboutScreen" }
                RootComponent.Child.About(aboutComponent(componentContext))
            }
            is Configuration.ThinkpadSettingsScreen -> {
                logger.d { "SettingsScreen" }
                RootComponent.Child.Settings(settingsComponent(componentContext))
            }
            else -> {
                logger.d { "Else Screen Block Called" }
                RootComponent.Child.HomePane(homePaneComponent(componentContext))
            }
        }

    private fun homePaneComponent(componentContext: ComponentContext): HomePaneComponent =
        HomePaneComponentImpl(
            componentContext = componentContext,
            onAboutClicked = {
                logger.d { "About Clicked" }
                NavigationActions.Home.onAboutClicked(router)
            },
            onDonateClicked = { NavigationActions.Home.onDonateClicked(router) },
            onSettingsClicked = { NavigationActions.Home.onSettingsClicked(router) }
        )

    private fun aboutComponent(componentContext: ComponentContext): AboutComponent =
        AboutComponentImpl(
            componentContext = componentContext,
            onBackClicked = { NavigationActions.goBack(router) }
        )

    private fun settingsComponent(componentContext: ComponentContext): SettingsComponent =
        SettingsComponentImpl(
            componentContext = componentContext,
            onBackClicked = { NavigationActions.goBack(router) }
        )

    private fun donateComponent(componentContext: ComponentContext): DonateComponent =
        DonateComponentImpl(
            componentContext = componentContext,
            onBackClicked = { NavigationActions.goBack(router) }
        )
}