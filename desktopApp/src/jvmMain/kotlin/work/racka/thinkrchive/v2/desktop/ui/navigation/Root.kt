package work.racka.thinkrchive.v2.desktop.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.ui.ExperimentalComposeUiApi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.router
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import work.racka.thinkrchive.v2.desktop.ui.screens.about.AboutScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.donate.DonateScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.settings.SettingsScreen
import kotlin.reflect.KClass

@Composable
private fun rememberLifecycle(): Lifecycle {
    val lifecycle = remember { LifecycleRegistry() }

    DisposableEffect(Unit) {
        lifecycle.resume()
        onDispose { lifecycle.destroy() }
    }

    return lifecycle
}

private const val KEY_STATE = "STATE"

@Composable
private fun rememberStateKeeper(): StateKeeper {
    val saveableStateRegistry: SaveableStateRegistry? = LocalSaveableStateRegistry.current

    val dispatcher =
        remember {
            StateKeeperDispatcher(
                saveableStateRegistry
                    ?.consumeRestored(KEY_STATE) as ParcelableContainer?
            )
        }

    if (saveableStateRegistry != null) {
        DisposableEffect(Unit) {
            val entry = saveableStateRegistry.registerProvider(KEY_STATE, dispatcher::save)
            onDispose { entry.unregister() }
        }
    }

    return dispatcher
}

@Composable
private fun rememberComponentContext(): ComponentContext {
    val lifecycle = rememberLifecycle()
    val stateKeeper = rememberStateKeeper()

    return remember {
        DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = stateKeeper
        )
    }
}

@Composable
fun <C : Parcelable> rememberRouter(
    initialConfiguration: () -> C,
    initialBackStack: () -> List<C>,
    configurationClass: KClass<out C>
): Router<C, Any> {
    val context = rememberComponentContext()

    return remember {
        context.router(
            initialConfiguration = initialConfiguration,
            initialBackStack = initialBackStack,
            configurationClass = configurationClass
        ) { configuration, _ -> configuration }
    }
}

@Composable
inline fun <reified C : Parcelable> rememberRouter(
    noinline initialConfiguration: () -> C,
    noinline initialBackStack: () -> List<C> = ::emptyList,
): Router<C, Any> = rememberRouter(
    initialConfiguration = initialConfiguration,
    initialBackStack = initialBackStack,
    configurationClass = C::class
)

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Root() {
    val router = rememberRouter<Configuration>(
        initialConfiguration = { Configuration.ThinkpadListScreen }
    )

    Children(
        routerState = router.state
    ) { screen ->
        when (val config = screen.configuration) {
            /*is Configuration.ThinkpadListScreen -> {
                ThinkpadListScreen()
                    .Screen(
                        router = router
                    )
            }
            is Configuration.ThinkpadDetailsScreen -> {

                ThinkpadDetailsScreen(config.model)
                    .Screen(
                        router = router
                    )
            }*/
            is Configuration.ThinkpadAboutScreen -> {
                AboutScreen(router)
            }
            is Configuration.DonationScreen -> {
                DonateScreen(router)
            }
            is Configuration.ThinkpadSettingsScreen -> {
                SettingsScreen(router)
            }
            else -> {}
        }
    }
}