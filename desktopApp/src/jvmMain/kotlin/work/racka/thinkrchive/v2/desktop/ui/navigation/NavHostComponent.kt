package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import work.racka.thinkrchive.v2.desktop.ui.screens.details.ThinkpadDetailsScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.list.ThinkpadListScreen

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
                    onDetailsClicked = {
                        router.push(Configuration.ThinkpadDetailsScreen(it))
                    }
                )
            }
            is Configuration.ThinkpadDetailsScreen -> {

                ThinkpadDetailsScreen(
                    componentContext = componentContext,
                    onBackClicked = router::pop,
                    model = config.model
                )
            }
            else -> {
                ThinkpadListScreen(
                    componentContext = componentContext,
                    onDetailsClicked = {
                        router.push(Configuration.ThinkpadDetailsScreen(it))
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        Children(
            routerState = router.state,
            animation = crossfadeScale()
        ) { child ->
            child.instance.render()
        }
    }
}