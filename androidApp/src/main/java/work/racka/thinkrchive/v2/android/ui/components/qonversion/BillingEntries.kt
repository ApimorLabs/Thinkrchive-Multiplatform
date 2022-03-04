package work.racka.thinkrchive.v2.android.ui.components.qonversion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import domain.Product
import work.racka.thinkrchive.v2.android.ui.components.ClickableEntry
import work.racka.thinkrchive.v2.android.ui.theme.Dimens
import work.racka.thinkrchive.v2.android.ui.theme.Shapes

fun LazyListScope.BillingEntries(
    modifier: Modifier = Modifier,
    products: List<Product>,
    hasPremium: Boolean,
    onProductClick: (Int) -> Unit = { }
) {
    if (hasPremium) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(Shapes.large)
                    .background(Color.Green)
            ) {
                Text(
                    text = "You have Premium!",
                    modifier = Modifier
                        .padding(Dimens.MediumPadding.size)
                )
            }
        }
    }

    itemsIndexed(products) { index: Int, product ->
        ClickableEntry(
            modifier = modifier
                .padding(
                    horizontal = Dimens.MediumPadding.size,
                    vertical = Dimens.SmallPadding.size
                ),
            title = product.title,
            subtitle = product.price,
            description = product.description,
            onEntryClick = {
                onProductClick(index)
            }
        )
    }
}