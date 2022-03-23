package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel

class HomePaneComponentImpl(
    componentContext: ComponentContext,
    private val onSettingsClicked: () -> Unit,
    private val onAboutClicked: () -> Unit,
    private val onDonateClicked: () -> Unit,
) : HomePaneComponent, ComponentContext by componentContext {
    private val logger = Logger.withTag("HomePaneComponent").d { "Component created" }

    private val viewModel: ThinkpadListViewModel by inject(ThinkpadListViewModel::class.java)

    override val state: StateFlow<ThinkpadListState.State>
        get() = viewModel.hostDesktop.state

    override val sideEffect: Flow<ThinkpadListSideEffect>
        get() = viewModel.hostDesktop.sideEffect

    override fun search(query: String) {
        viewModel.hostDesktop.getSortedThinkpadList(query)
    }

    override fun sortOptionClicked(sort: Int) {
        viewModel.hostDesktop.sortSelected(sort)
    }

    override fun settingsClicked() {
        onSettingsClicked()
    }

    override fun aboutClicked() {
        onAboutClicked
    }

    override fun donateClicked() {
        onDonateClicked()
    }
}