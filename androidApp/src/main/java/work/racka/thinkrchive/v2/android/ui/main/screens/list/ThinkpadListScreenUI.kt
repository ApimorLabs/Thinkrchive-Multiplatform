package work.racka.thinkrchive.v2.android.ui.main.screens.list

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import domain.Thinkpad
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.v2.android.ui.components.CustomSearchBar
import work.racka.thinkrchive.v2.android.ui.components.FloatingScrollToTopButton
import work.racka.thinkrchive.v2.android.ui.components.HomeBottomSheet
import work.racka.thinkrchive.v2.android.ui.components.ThinkpadEntry
import work.racka.thinkrchive.v2.android.ui.theme.Dimens
import work.racka.thinkrchive.v2.android.ui.theme.Shapes
import work.racka.thinkrchive.v2.android.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.v2.android.utils.Constants


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkpadListScreenUI(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onEntryClick: (Thinkpad) -> Unit = { },
    onSearch: (String) -> Unit = { },
    onSortOptionClicked: (Int) -> Unit = { },
    onSettingsClicked: () -> Unit = { },
    onAboutClicked: () -> Unit = { },
    onDonateClicked: () -> Unit = { },
    onRefresh: () -> Unit = { },
    currentSortOption: Int,
    thinkpadList: List<Thinkpad>,
    networkLoading: Boolean,
    networkError: String
) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(
                    modifier = Modifier
                        .navigationBarsPadding(),
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colorScheme.inverseSurface,
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionColor = MaterialTheme.colorScheme.inversePrimary,
                    shape = Shapes.large
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        ModalBottomSheetLayout(
            sheetContent = {
                HomeBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    onSortOptionClicked = {
                        onSortOptionClicked(it)
                    },
                    currentSortOption = currentSortOption,
                    onSettingsClicked = onSettingsClicked,
                    onDonateClicked = onDonateClicked,
                    onAboutClicked = onAboutClicked
                )
            },
            sheetState = sheetState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = networkLoading),
                onRefresh = { onRefresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(vertical = Dimens.MediumPadding.size),
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                LazyColumn(
                    state = listState,
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Timber.d("thinkpadListScreen Contents called")
                    item {
                        Spacer(modifier = Modifier.statusBarsPadding())
                        CustomSearchBar(
                            focusManager = focusManager,
                            onSearch = {
                                onSearch(it)
                            },
                            onDismissSearchClicked = {
                                onSearch("")
                            },
                            onOptionsClicked = {
                                scope.launch {
                                    sheetState.show()
                                }
                            },
                            modifier = Modifier.padding(
                                vertical = Dimens.SmallPadding.size
                            )
                        )
                    }
                    items(thinkpadList) {
                        ThinkpadEntry(
                            thinkpad = it,
                            onEntryClick = { onEntryClick(it) },
                            modifier = Modifier
                                .padding(
                                    horizontal = Dimens.MediumPadding.size,
                                    vertical = Dimens.SmallPadding.size
                                )
                        )
                    }
                    item {
                        if (networkError.isNotBlank()) {
                            Text(
                                text = networkError,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // This must always stay at the bottom for navBar padding
                    item {
                        Spacer(modifier = Modifier.navigationBarsPadding())
                    }
                }
            }

            if (!networkLoading) {
                FloatingScrollToTopButton(
                    listState = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    scope = scope
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun ThinkpadListScreenPreview() {
    ThinkRchiveTheme {
        ThinkpadListScreenUI(
            thinkpadList = Constants.ThinkpadsListPreview,
            networkLoading = false,
            networkError = "",
            currentSortOption = 0
        )
    }
}
