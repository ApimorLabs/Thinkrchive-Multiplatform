package work.racka.thinkrchive.v2.desktop.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import work.racka.thinkrchive.v2.desktop.ui.theme.Dimens
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.v2.desktop.utils.StringResource

// Must always be used inside a BoxScope for proper alignment
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToolbarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {

    val density = LocalDensity.current

    var imageLoading by remember {
        mutableStateOf(true)
    }

    AsyncImage(
        load = { /*loadSvgPainter(thinkpad.imageUrl, density)*/ },
        painterFor = { painterResource("drawables/app_icon.png") },
        contentDescription = StringResource.thinkpad_image,
        onStart = {
            imageLoading = true
        },
        onSuccess = {
            imageLoading = false
        },
        onError = {
            imageLoading = false
        },
        modifier = modifier
            .padding(Dimens.MediumPadding.size)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
    )
    AnimatedVisibility(
        visible = imageLoading,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun TopCardWithImagePreview() {
    ThinkRchiveTheme {
        ToolbarImage(
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
        )
    }
}
