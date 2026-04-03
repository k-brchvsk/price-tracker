package dev.kigya.pricely.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PricelyColorScheme(
    val background: Color,
    val surface: Color,

    val textPrimary: Color,
    val textSecondary: Color,
    val textErrorDescription: Color,

    val primary: Color,
    val primaryInactive: Color,

    val success: Color,
    val error: Color,

    val cardBorder: Color,

    val loadingIndicatorTrack: Color,
    val loadingIndicatorActive: Color,

    val connectionIcon: Color,
    val backIcon: Color,
)

fun pricelyColorScheme(
    isDark: Boolean,
    colors: PricelyColor = PricelyColor(),
): PricelyColorScheme = PricelyColorScheme(
    background = PricelyDynamicColor(
        light = colors.white,
        dark = colors.greyscale600,
    ).resolve(isDark),

    surface = PricelyDynamicColor(
        light = colors.white,
        dark = colors.greyscale600,
    ).resolve(isDark),

    textPrimary = PricelyDynamicColor(
        light = colors.greyscale900,
        dark = colors.greyscale0,
    ).resolve(isDark),

    textSecondary = colors.greyscale400,

    textErrorDescription = PricelyDynamicColor(
        light = colors.greyscale500,
        dark = colors.greyscale300,
    ).resolve(isDark),

    primary = PricelyDynamicColor(
        light = colors.primary100,
        dark = colors.primary0,
    ).resolve(isDark),

    primaryInactive = colors.primary0,

    success = colors.successDark,
    error = colors.errorDark,

    cardBorder = PricelyDynamicColor(
        light = colors.greyscale300,
        dark = colors.greyscale400,
    ).resolve(isDark),

    loadingIndicatorTrack = PricelyDynamicColor(
        light = colors.primary0,
        dark = colors.greyscale300,
    ).resolve(isDark),

    loadingIndicatorActive = PricelyDynamicColor(
        light = colors.primary300,
        dark = colors.greyscale0,
    ).resolve(isDark),

    connectionIcon = colors.primary300,

    backIcon = PricelyDynamicColor(
        light = colors.greyscale600,
        dark = colors.greyscale0,
    ).resolve(isDark),
)

val LocalPricelyColors = staticCompositionLocalOf<PricelyColorScheme> {
    error("No PricelyColorScheme provided")
}
