package work.racka.thinkrchive.v2.desktop.ui.screens.about

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import states.about.AboutSideEffect
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.about.AboutComponent

@Composable
fun AboutScreen(
    aboutComponent: AboutComponent
) {
    val state by aboutComponent.state.collectAsState()
    val sideEffect = aboutComponent.sideEffect
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
            aboutComponent.update()
        },
        onBackButtonPressed = {
            aboutComponent.backClicked()
        },
        appAbout = state.appAbout,
        hasUpdates = state.hasUpdates
    )
}
