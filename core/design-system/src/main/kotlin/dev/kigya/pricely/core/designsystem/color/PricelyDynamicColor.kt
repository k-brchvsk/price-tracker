package dev.kigya.pricely.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
internal data class PricelyDynamicColor(
    val light: Color,
    val dark: Color,
) {
    fun resolve(isDark: Boolean): Color = if (isDark) dark else light
}
