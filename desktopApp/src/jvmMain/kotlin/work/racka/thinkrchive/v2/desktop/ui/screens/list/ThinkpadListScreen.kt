package work.racka.thinkrchive.v2.desktop.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import org.koin.java.KoinJavaComponent.inject
import states.list.ThinkpadListSideEffect
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component

class ThinkpadListScreen(
    private val componentContext: ComponentContext,
    private val onEntryClick: (model: String) -> Unit,
    private val onSettingsClicked: () -> Unit,
    private val onAboutClicked: () -> Unit,
    private val onDonateClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    private val viewModel: ThinkpadListViewModel by inject(ThinkpadListViewModel::class.java)

    @Composable
    override fun render() {
        val host = viewModel.hostDesktop
        val state by viewModel.hostDesktop.state.collectAsState()
        val sideEffect = viewModel.hostDesktop.sideEffect
            .collectAsState(initial = ThinkpadListSideEffect.Network())
            .value as ThinkpadListSideEffect.Network

        ThinkpadListScreenUI(
            thinkpadList = state.thinkpadList,
            networkLoading = sideEffect.isLoading,
            onSearch = { query ->
                host.getSortedThinkpadList(query)
            },
            onEntryClick = { thinkpad ->
                onEntryClick(thinkpad.model)
            },
            networkError = sideEffect.errorMsg,
            currentSortOption = state.sortOption,
            onSortOptionClicked = { sort ->
                host.sortSelected(sort)
            },
            onSettingsClicked = onSettingsClicked,
            onAboutClicked = onAboutClicked,
            onDonateClicked = onDonateClicked
        )
    }
}
