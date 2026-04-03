package dev.kigya.pricely.core.designsystem.color

import androidx.compose.runtime.staticCompositionLocalOf

val LocalPricelyColors = staticCompositionLocalOf<PricelyColorScheme> {
    error("No PricelyColorScheme provided")
}
