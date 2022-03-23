package work.racka.thinkrchive.v2.desktop.ui.screens.splitpane

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
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
    val pane: MutableState<PaneConfig> = remember {
        mutableStateOf(PaneConfig.Blank)
    }

    logger.d { "HomeSplitPaneScreen called" }

    val listState by homePaneComponent.state.collectAsState()
    val listSideEffect = homePaneComponent.sideEffect
        .collectAsState(initial = ThinkpadListSideEffect.Network())
        .value as ThinkpadListSideEffect.Network

    HorizontalSplitPane(
        splitPaneState = splitterState
    ) {

        first(600.dp) {
            ListPane(
                state = listState,
                sideEffect = listSideEffect,
                onEntryClick = {
                    pane.value = PaneConfig.Details(it)
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
                when (pane.value) {
                    is PaneConfig.Details -> {
                        DetailsPane(
                            onBackClicked = {
                                pane.value = PaneConfig.Blank
                            },
                            model = (pane.value as PaneConfig.Details).model
                        ).render()
                    }
                    else -> {
                        // Show Empty Composable
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