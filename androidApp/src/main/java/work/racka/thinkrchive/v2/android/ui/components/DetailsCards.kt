package work.racka.thinkrchive.v2.android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import work.racka.thinkrchive.v2.android.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.v2.android.utils.Constants
import work.racka.thinkrchive.v2.common.database.model.Thinkpad

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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailsCardsPreview() {
    ThinkRchiveTheme {
        DetailsCards(thinkpad = Constants.ThinkpadsListPreview[0])
    }
}
