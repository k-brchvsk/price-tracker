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
    val descriptionCardBorder: Color,
    val descriptionText: Color,
    val loadingIndicatorTrack: Color,
    val loadingIndicatorActive: Color,
    val actionButtonConnectedBackground: Color,
    val actionButtonDisconnectedBackground: Color,
    val actionButtonIconConnected: Color,
    val actionButtonIconDisconnected: Color,
)

internal fun pricelyColorScheme(
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
        dark = colors.greyscale25,
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
    descriptionCardBorder = PricelyDynamicColor(
        light = colors.greyscale200,
        dark = colors.greyscale400,
    ).resolve(isDark),
    descriptionText = colors.greyscale500,
    loadingIndicatorTrack = PricelyDynamicColor(
        light = colors.primary0,
        dark = colors.greyscale300,
    ).resolve(isDark),
    loadingIndicatorActive = PricelyDynamicColor(
        light = colors.primary300,
        dark = colors.greyscale25,
    ).resolve(isDark),
    actionButtonConnectedBackground = PricelyDynamicColor(
        light = colors.primary100,
        dark = colors.primary0,
    ).resolve(isDark),
    actionButtonDisconnectedBackground = PricelyDynamicColor(
        light = colors.primary0,
        dark = colors.greyscale25,
    ).resolve(isDark),
    actionButtonIconConnected = PricelyDynamicColor(
        light = colors.white,
        dark = colors.primary300,
    ).resolve(isDark),
    actionButtonIconDisconnected = PricelyDynamicColor(
        light = colors.primary100,
        dark = colors.primary300,
    ).resolve(isDark),
)

internal val LocalPricelyColors = staticCompositionLocalOf<PricelyColorScheme> {
    error("No PricelyColorScheme provided")
}
