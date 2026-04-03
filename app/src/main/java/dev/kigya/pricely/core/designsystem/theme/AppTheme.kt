package dev.kigya.pricely.core.designsystem.theme

import androidx.compose.runtime.Composable
import dev.kigya.pricely.core.designsystem.color.LocalPricelyColors
import dev.kigya.pricely.core.designsystem.color.PricelyColorScheme

object AppTheme {
    val colors: PricelyColorScheme
        @Composable get() = LocalPricelyColors.current
}
