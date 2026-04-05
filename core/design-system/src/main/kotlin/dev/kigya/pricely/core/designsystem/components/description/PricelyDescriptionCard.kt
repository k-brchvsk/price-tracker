package dev.kigya.pricely.core.designsystem.components.description

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.theme.AppTheme

@Composable
fun PricelyDescriptionCard(
    text: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = LocalShapes.current.medium,
        border = BorderStroke(
            width = AppTheme.dimens.borderThin,
            color = AppTheme.colors.descriptionCardBorder,
        ),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            modifier = Modifier.padding(AppTheme.dimens.space12),
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
