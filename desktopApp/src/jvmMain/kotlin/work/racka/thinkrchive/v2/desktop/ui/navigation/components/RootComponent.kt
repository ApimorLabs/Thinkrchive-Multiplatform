package work.racka.thinkrchive.v2.desktop.ui.navigation.components

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import work.racka.thinkrchive.v2.desktop.ui.navigation.Configuration
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.about.AboutComponent
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.donate.DonateComponent
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.home.HomePaneComponent
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.settings.SettingsComponent

interface RootComponent {
    val routerState: Value<RouterState<Configuration, Child>>

    sealed class Child {
        data class HomePane(val component: HomePaneComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
        data class About(val component: AboutComponent) : Child()
        data class Donate(val component: DonateComponent) : Child()
    }
}
