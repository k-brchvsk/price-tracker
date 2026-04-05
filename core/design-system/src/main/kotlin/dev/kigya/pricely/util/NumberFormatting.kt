package dev.kigya.pricely.util

import java.util.Locale
import kotlin.math.abs

private const val PLUS_PERCENT_FORMAT = "+ %.2f%%"
private const val MINUS_PERCENT_FORMAT = "- %.2f%%"

fun formatPrice(value: Double): String = "$" + String.format(Locale.US, "%.2f", value)

fun formatPercent(percentChange: Double?): String? {
    if (percentChange == null) return null
    return if (percentChange < 0) {
        String.format(Locale.US, MINUS_PERCENT_FORMAT, abs(percentChange))
    } else {
        String.format(Locale.US, PLUS_PERCENT_FORMAT, percentChange)
    }
}
