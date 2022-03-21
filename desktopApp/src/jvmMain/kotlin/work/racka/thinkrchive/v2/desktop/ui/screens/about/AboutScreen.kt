package work.racka.thinkrchive.v2.desktop.ui.screens.about

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import org.koin.java.KoinJavaComponent.inject
import states.about.AboutSideEffect
import work.racka.thinkrchive.v2.common.features.about.viewmodel.AboutViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component

class AboutScreen(
    private val componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    private val viewModel: AboutViewModel by inject(AboutViewModel::class.java)

    @Composable
    override fun render() {
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
            onBackButtonPressed = onBackClicked,
            appAbout = state.appAbout,
            hasUpdates = state.hasUpdates
        )
    }

}
