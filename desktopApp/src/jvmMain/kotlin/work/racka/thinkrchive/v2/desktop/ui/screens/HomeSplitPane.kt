package work.racka.thinkrchive.v2.desktop.ui.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component

sealed class SplitPaneConfig()

class HomeSplitPane(
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {

    @Composable
    override fun render() {
        val splitterState = rememberSplit
    }
}