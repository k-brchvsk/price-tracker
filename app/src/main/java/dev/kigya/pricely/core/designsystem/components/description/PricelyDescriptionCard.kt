package dev.kigya.pricely.core.designsystem.components.description

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import androidx.compose.material3.Card as MaterialCard
import androidx.compose.material3.CardDefaults as MaterialCardDefaults

@Composable
fun PricelyDescriptionCard(
    text: String,
    modifier: Modifier = Modifier,
) {
    MaterialCard(
        modifier = modifier,
        shape = LocalShapes.current.medium,
        border = BorderStroke(
            width = AppTheme.dimens.borderThin,
            color = AppTheme.colors.descriptionCardBorder,
        ),
        colors = MaterialCardDefaults.cardColors(
            containerColor = AppTheme.colors.surface,
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(AppTheme.dimens.space12),
            contentAlignment = Alignment.Center,
        ) {
            PricelyText(
                text = text,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.descriptionText,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DescriptionCard_Light() {
    PricelyPreview(isDark = false) {
        PricelyDescriptionCard(
            text = "Netflix, Inc. is an American subscription streaming service and production company. Founded in 1997 by Reed Hastings and Marc Randolph in Scotts Valley, California.",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DescriptionCard_Dark() {
    PricelyPreview(isDark = true) {
        PricelyDescriptionCard(
            text = "Netflix, Inc. is an American subscription streaming service and production company. Founded in 1997 by Reed Hastings and Marc Randolph in Scotts Valley, California.",
        )
    }
}
