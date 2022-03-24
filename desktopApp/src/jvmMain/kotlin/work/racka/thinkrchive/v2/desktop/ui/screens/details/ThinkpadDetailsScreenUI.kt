package work.racka.thinkrchive.v2.desktop.ui.screens.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import domain.Thinkpad
import work.racka.thinkrchive.v2.desktop.ui.components.CollapsingToolbarBase
import work.racka.thinkrchive.v2.desktop.ui.components.DetailsCards
import work.racka.thinkrchive.v2.desktop.ui.components.ToolbarImage
import work.racka.thinkrchive.v2.desktop.ui.theme.Dimens
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.v2.desktop.utils.Constants

@Composable
fun ThinkpadDetailsScreenUI(
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad,
    onCloseButtonClicked: () -> Unit = { },
    onExternalLinkClicked: () -> Unit = { },
    listState: LazyListState = rememberLazyListState()
) {
    // CollapsingToolbar Implementation
    val toolbarHeight = 250.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onCloseButtonClicked = onCloseButtonClicked
            ) {
                ToolbarImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageUrl = thinkpad.imageUrl
                )
            }
        }
    ) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                DetailsCards(
                    thinkpad = thinkpad,
                    onExternalLinkClick = onExternalLinkClicked
                )
            }

            // Always at the bottom
            item { Spacer(modifier = Modifier.padding(Dimens.MediumPadding.size)) }
        }
    }
}


@ExperimentalAnimationApi
@Preview
@Composable
private fun ThinkpadDetailsScreenPreview() {
    ThinkRchiveTheme {
        ThinkpadDetailsScreenUI(thinkpad = Constants.ThinkpadsListPreview[0])
    }
}
