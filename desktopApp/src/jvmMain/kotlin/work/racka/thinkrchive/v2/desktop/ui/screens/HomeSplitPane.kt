package work.racka.thinkrchive.v2.desktop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.koin.java.KoinJavaComponent.inject
import work.racka.thinkrchive.v2.common.features.list.viewmodel.ThinkpadListViewModel
import work.racka.thinkrchive.v2.desktop.ui.navigation.Component
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.DetailsScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.ListScreen
import work.racka.thinkrchive.v2.desktop.ui.screens.splitpane.PaneConfig
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

class HomeSplitPane(
    private val componentContext: ComponentContext,
    private val onSettingsClicked: () -> Unit,
    private val onAboutClicked: () -> Unit,
    private val onDonateClicked: () -> Unit
) : Component, ComponentContext by componentContext {

    // Called here so data doesn't get recomputed every time an event is consumed
    private val listViewModel: ThinkpadListViewModel by inject(ThinkpadListViewModel::class.java)

    @OptIn(ExperimentalSplitPaneApi::class)
    @Composable
    override fun render() {
        val splitterState = rememberSplitPaneState()
        val pane: MutableState<PaneConfig> = remember {
            mutableStateOf(PaneConfig.Blank)
        }

        HorizontalSplitPane(
            splitPaneState = splitterState
        ) {

            first(600.dp) {
                ListScreen(
                    viewModel = listViewModel,
                    onEntryClick = {
                        pane.value = PaneConfig.Details(it)
                    },
                    onSettingsClicked = {
                        onSettingsClicked()
                    },
                    onAboutClicked = onAboutClicked,
                    onDonateClicked = onDonateClicked
                )
            }

            second(700.dp) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    when (pane.value) {
                        is PaneConfig.Details -> {
                            DetailsScreen(
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
}