package work.racka.thinkrchive.v2.desktop.ui.navigation

import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push

object NavigationActions {

    fun goBack(router: Router<Configuration, Component>) {
        router.pop()
    }

    object List {
        fun onEntryClick(
            router: Router<Configuration, Component>,
            model: String
        ) {
            router.push(Configuration.ThinkpadDetailsScreen(model))
        }

        fun onSettingsClicked(router: Router<Configuration, Component>) {
            router.push(Configuration.ThinkpadSettingsScreen)
        }

        fun onAboutClicked(router: Router<Configuration, Component>) {
            router.push(Configuration.ThinkpadAboutScreen)
        }

        fun onDonateClicked(router: Router<Configuration, Component>) {
            router.push(Configuration.DonationScreen)
        }
    }
}