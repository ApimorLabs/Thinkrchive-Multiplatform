package work.racka.thinkrchive.v2.desktop.ui.navigation

import com.arkivanov.essenty.parcelable.Parcelable

sealed class Configuration : Parcelable {
    object HomeSplitPane : Configuration()
    object DonationScreen : Configuration()
    object ThinkpadAboutScreen : Configuration()
    object ThinkpadSettingsScreen : Configuration()
}