package work.racka.thinkrchive.v2.desktop.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import domain.Thinkpad
import work.racka.thinkrchive.v2.desktop.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.v2.desktop.utils.Constants

@Composable
fun DetailsCards(
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad,
    onExternalLinkClick: () -> Unit = { }
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThinkpadDetails(
            thinkpad = thinkpad,
            onExternalLinkClick = onExternalLinkClick
        )
        ThinkpadFeatures(thinkpad = thinkpad)
    }

}

@Preview
@Composable
private fun DetailsCardsPreview() {
    ThinkRchiveTheme {
        DetailsCards(thinkpad = Constants.ThinkpadsListPreview[0])
    }
}
