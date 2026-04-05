package dev.kigya.pricely.util

import java.util.Locale
import kotlin.math.abs

fun formatPrice(value: Double): String =
    "$" + String.format(Locale.US, "%.2f", value)

fun formatPercent(percentChange: Double?): String? {
    if (percentChange == null) return null
    return when {
        percentChange > 0 -> String.format(Locale.US, "+ %.2f%%", percentChange)
        percentChange < 0 -> String.format(Locale.US, "- %.2f%%", abs(percentChange))
        else -> String.format(Locale.US, "+ %.2f%%", percentChange)
    }
}
