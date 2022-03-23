package work.racka.thinkrchive.v2.desktop.ui.navigation

import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push

object NavigationActions {

    fun goBack(router: Router<Configuration, RootComponent.Child>) {
        router.pop()
    }

    object Home {

        fun onSettingsClicked(router: Router<Configuration, RootComponent.Child>) {
            router.push(Configuration.ThinkpadSettingsScreen)
        }

        fun onAboutClicked(router: Router<Configuration, RootComponent.Child>) {
            router.push(Configuration.ThinkpadAboutScreen)
        }

        fun onDonateClicked(router: Router<Configuration, RootComponent.Child>) {
            router.push(Configuration.DonationScreen)
        }
    }
}