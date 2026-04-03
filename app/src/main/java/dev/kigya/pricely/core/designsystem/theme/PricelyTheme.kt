package dev.kigya.pricely.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.kigya.pricely.core.designsystem.color.LocalPricelyColors
import dev.kigya.pricely.core.designsystem.color.pricelyColorScheme

@Composable
fun PricelyTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = pricelyColorScheme(isDark)

    CompositionLocalProvider(
        LocalPricelyColors provides colors
    ) {
        content()
    }
}
