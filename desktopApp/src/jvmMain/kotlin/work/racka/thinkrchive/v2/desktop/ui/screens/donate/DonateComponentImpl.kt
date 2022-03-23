package work.racka.thinkrchive.v2.desktop.ui.screens.donate

import com.arkivanov.decompose.ComponentContext

class DonateComponentImpl(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : DonateComponent, ComponentContext by componentContext {
    override fun backClicked() {
        onBackClicked()
    }
}