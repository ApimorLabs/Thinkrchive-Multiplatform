package work.racka.thinkrchive.v2.desktop.ui.screens.about

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.pop
import org.koin.java.KoinJavaComponent.inject
import states.about.AboutSideEffect
import work.racka.thinkrchive.v2.common.features.about.viewmodel.AboutViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Configuration

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AboutScreen(
    router: Router<Configuration, Any>
) {
    val viewModel: AboutViewModel by inject(AboutViewModel::class.java)
    val state by viewModel.host.state.collectAsState()
    val sideEffect = viewModel.host.sideEffect
        .collectAsState(initial = AboutSideEffect.NoSideEffect)
        .value

    // React to Side Effects
    when (sideEffect) {
        is AboutSideEffect.UpdateFound -> {
            //ShowToastInCompose(message = sideEffect.toastMessage)
        }
        is AboutSideEffect.UpdateNotFound -> {
            //ShowToastInCompose(message = sideEffect.toastMessage)
        }
        is AboutSideEffect.Update -> {
            // Call Update Logic Here
        }
        is AboutSideEffect.NoSideEffect -> {
            /* Do Nothing */
        }
    }

    AboutScreenUI(
        onCheckUpdates = {
            viewModel.host.update()
        },
        onBackButtonPressed = router::pop,
        appAbout = state.appAbout,
        hasUpdates = state.hasUpdates
    )
}