package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import states.list.ThinkpadListSideEffect
import work.racka.thinkrchive.v2.desktop.ui.navigation.components.home.HomePaneComponent
import work.racka.thinkrchive.v2.desktop.ui.screens.details.DetailsPane
import work.racka.thinkrchive.v2.desktop.ui.screens.list.ListPane
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun HomeSplitPaneScreen(
    homePaneComponent: HomePaneComponent
) {
    val logger = Logger.withTag("HomeSplitPaneScreen")
    val splitterState = rememberSplitPaneState()
    val paneState = homePaneComponent.paneState.collectAsState()

    logger.d { "HomeSplitPaneScreen called" }

    val listState by homePaneComponent.state.collectAsState()
    val listSideEffect = homePaneComponent.sideEffect
        .collectAsState(initial = ThinkpadListSideEffect.NoSideEffect)
        .value

    HorizontalSplitPane(
        splitPaneState = splitterState
    ) {

        first(600.dp) {
            ListPane(
                state = listState,
                sideEffect = listSideEffect,
                onEntryClick = {
                    logger.d { "Thinkpad Clicked: $it" }
                    homePaneComponent.updatePaneState(PaneConfig.Details(it))
                },
                onSearch = { query ->
                    homePaneComponent.search(query)
                },
                onSortOptionClicked = { sort ->
                    homePaneComponent.sortOptionClicked(sort)
                },
                onSettingsClicked = {
                    homePaneComponent.settingsClicked()
                },
                onAboutClicked = {
                    homePaneComponent.aboutClicked()
                },
                onDonateClicked = {
                    logger.d { "About Clicked in Pane" }
                    homePaneComponent.donateClicked()
                }
            )
        }

        second(700.dp) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                when (paneState.value) {
                    is PaneConfig.Details -> {
                        val detailsPane = remember(paneState.value) {
                            DetailsPane(
                                onCloseClicked = {
                                    homePaneComponent.updatePaneState(PaneConfig.Blank)
                                },
                                model = (paneState.value as PaneConfig.Details).model
                            )
                        }
                        detailsPane.render()
                    }
                    else -> {
                        // Show Empty Composable
                        Text(text = "Nothing to Show")
                    }
                }
            }
        }

        splitter {
            visiblePart {
                Box(
                    Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.background)
                )
            }
            handle {
                Box(
                    Modifier
                        .markAsHandle()
                        .cursorForHorizontalResize()
                        .background(
                            color = MaterialTheme.colors.onBackground
                                .copy(alpha = .2f),
                            shape = CircleShape
                        )
                        .width(4.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}