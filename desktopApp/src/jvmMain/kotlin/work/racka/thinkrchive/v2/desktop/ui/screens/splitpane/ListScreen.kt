package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel
import work.racka.thinkrchive.v2.desktop.ui.screens.list.ThinkpadListScreenUI


@Composable
fun ListScreen(
    viewModel: ThinkpadListViewModel,
    onEntryClick: (model: String) -> Unit,
    onSettingsClicked: () -> Unit,
    onAboutClicked: () -> Unit,
    onDonateClicked: () -> Unit
) {
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

@Composable
fun ListPane(
    state: ThinkpadListState.State,
    sideEffect: ThinkpadListSideEffect.Network,
    onSearch: (String) -> Unit,
    onEntryClick: (model: String) -> Unit,
    onSortOptionClicked: (Int) -> Unit,
    onSettingsClicked: () -> Unit,
    onAboutClicked: () -> Unit,
    onDonateClicked: () -> Unit
) {
    ThinkpadListScreenUI(
        thinkpadList = state.thinkpadList,
        networkLoading = sideEffect.isLoading,
        onSearch = { query ->
            onSearch(query)
        },
        onEntryClick = { thinkpad ->
            onEntryClick(thinkpad.model)
        },
        networkError = sideEffect.errorMsg,
        currentSortOption = state.sortOption,
        onSortOptionClicked = { sort ->
            onSortOptionClicked(sort)
        },
        onSettingsClicked = onSettingsClicked,
        onAboutClicked = onAboutClicked,
        onDonateClicked = onDonateClicked
    )
}
