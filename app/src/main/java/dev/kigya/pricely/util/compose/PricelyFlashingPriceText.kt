package dev.kigya.pricely.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import kotlinx.coroutines.delay

private const val PRICE_FLASH_DURATION = 1_000L

@Composable
fun PricelyFlashingPriceText(
    text: String,
    trend: PricelyTrend,
    emphasisColor: Color,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    var lastPriceSeen by remember { mutableStateOf<String?>(null) }
    var flashTrend by remember { mutableStateOf<PricelyTrend?>(null) }

    LaunchedEffect(text, trend) {
        val previous = lastPriceSeen
        val priceChanged = previous != null && previous != text
        lastPriceSeen = text
        if (!priceChanged) return@LaunchedEffect
        when (trend) {
            PricelyTrend.UP, PricelyTrend.DOWN -> {
                try {
                    flashTrend = trend
                    delay(PRICE_FLASH_DURATION)
                } finally {
                    flashTrend = null
                }
            }
            PricelyTrend.NEUTRAL -> Unit
        }
    }

    val color = when (flashTrend) {
        PricelyTrend.UP -> AppTheme.colors.success
        PricelyTrend.DOWN -> AppTheme.colors.error
        null, PricelyTrend.NEUTRAL -> emphasisColor
    }

    PricelyText(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
    )
}
