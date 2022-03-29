package work.racka.thinkrchive.v2.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.racka.thinkrchive.v2.android.ui.theme.Dimens
import work.racka.thinkrchive.v2.android.ui.theme.ThinkRchiveTheme

@ExperimentalAnimationApi
@Composable
fun FloatingScrollButtonAndLoadingIndicator(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    scope: CoroutineScope,
    networkLoading: Boolean
) {
    val showScrollButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    AnimatedVisibility(
        visible = networkLoading,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Box(
            modifier = modifier
                .padding(vertical = Dimens.MediumPadding.size),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(Dimens.SmallPadding.size)
                        .size(24.dp)
                )
            }
        }
    }

    AnimatedVisibility(
        visible = showScrollButton && !networkLoading,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Box(
            modifier = modifier
                .padding(vertical = Dimens.MediumPadding.size),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = Dimens.MediumPadding.size)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowUpward,
                        contentDescription = "Scroll Up button"
                    )
                    Spacer(modifier = Modifier.width(Dimens.SmallPadding.size))
                    Text(
                        text = "Scroll Up",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ScrollButtonPreview() {
    ThinkRchiveTheme {
        FloatingScrollButtonAndLoadingIndicator(
            listState = rememberLazyListState(),
            scope = rememberCoroutineScope(),
            networkLoading = false
        )
    }
}
