package work.racka.thinkrchive.v2.desktop.ui.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Configuration : Parcelable {
    @Parcelize
    object ThinkpadListScreen : Configuration()

    @Parcelize
    data class ThinkpadDetailsScreen(val model: String) : Configuration()

    @Parcelize
    object ThinkpadSettingsScreen : Configuration()

    @Parcelize
    object ThinkpadAboutScreen : Configuration()

    @Parcelize
    object DonationScreen : Configuration()
}