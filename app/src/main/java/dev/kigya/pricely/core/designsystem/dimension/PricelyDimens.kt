package dev.kigya.pricely.core.designsystem.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data object PricelyDimens {
    val space4 = 4.dp
    val space8 = 8.dp
    val space12 = 12.dp
    val space16 = 16.dp
    val space24 = 24.dp
    val space32 = 32.dp
    val space48 = 48.dp

    val icon = 24.dp
    val iconSmall = 16.dp
    val iconContainer = 40.dp

    val borderThin = 1.dp
    val statusDot = 8.dp

    val actionButtonSize = 24.dp
    val actionIconSize = 12.dp
}

val LocalDimens = staticCompositionLocalOf<PricelyDimens> {
    error("No PricelyDimens provided")
}
