package work.racka.thinkrchive.v2.desktop.ui.navigation.components.about

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject
import states.about.AboutSideEffect
import states.about.AboutState
import work.racka.thinkrchive.v2.common.features.about.viewmodel.AboutViewModel

class AboutComponentImpl(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
) : AboutComponent, ComponentContext by componentContext {

    private val viewModel: AboutViewModel by inject(AboutViewModel::class.java)

    override val state: StateFlow<AboutState.State>
        get() = viewModel.host.state

    override val sideEffect: Flow<AboutSideEffect>
        get() = viewModel.host.sideEffect

    override fun backClicked() {
        onBackClicked()
    }

    override fun update() {
        viewModel.host.update()
    }

    init {
        lifecycle.doOnDestroy {
            viewModel.destroy()
        }
    }
}