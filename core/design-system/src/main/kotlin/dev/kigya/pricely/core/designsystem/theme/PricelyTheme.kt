package dev.kigya.pricely.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.kigya.pricely.core.designsystem.color.LocalPricelyColors
import dev.kigya.pricely.core.designsystem.color.pricelyColorScheme
import dev.kigya.pricely.core.designsystem.dimension.LocalDimens
import dev.kigya.pricely.core.designsystem.dimension.PricelyDimens
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.shape.PricelyShapes
import dev.kigya.pricely.core.designsystem.typography.LocalTypography
import dev.kigya.pricely.core.designsystem.typography.PricelyTypography

@Composable
fun PricelyTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = pricelyColorScheme(isDark)

    CompositionLocalProvider(
        LocalPricelyColors provides colors,
        LocalTypography provides PricelyTypography,
        LocalDimens provides PricelyDimens,
        LocalShapes provides PricelyShapes,
    ) {
        MaterialTheme {
            content()
        }
    }
}
