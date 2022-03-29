package work.racka.thinkrchive.v2.desktop.ui.screens.list

import androidx.compose.runtime.Composable
import states.list.ThinkpadListSideEffect
import states.list.ThinkpadListState

@Composable
fun ListPane(
    state: ThinkpadListState.State,
    sideEffect: ThinkpadListSideEffect,
    onSearch: (String) -> Unit,
    onEntryClick: (model: String) -> Unit,
    onSortOptionClicked: (Int) -> Unit,
    onSettingsClicked: () -> Unit,
    onAboutClicked: () -> Unit,
    onDonateClicked: () -> Unit
) {
    when (sideEffect) {
        is ThinkpadListSideEffect.ShowNetworkErrorSnackbar -> {}
        else -> {}
    }
    ThinkpadListScreenUI(
        thinkpadList = state.thinkpadList,
        networkLoading = state.networkLoading,
        onSearch = { query ->
            onSearch(query)
        },
        onEntryClick = { thinkpad ->
            onEntryClick(thinkpad.model)
        },
        networkError = "",
        currentSortOption = state.sortOption,
        onSortOptionClicked = { sort ->
            onSortOptionClicked(sort)
        },
        onSettingsClicked = onSettingsClicked,
        onAboutClicked = onAboutClicked,
        onDonateClicked = onDonateClicked
    )
}
