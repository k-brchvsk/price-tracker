package dev.kigya.pricely.domain.logic

import kotlin.math.abs

object QuotePriceMath {

    private const val PRICE_COMPARISON_EPSILON = 1e-9
    private const val PERCENT_SCALE = 100.0

    fun percentChange(
        previousPrice: Double,
        currentPrice: Double,
    ): Double? {
        if (abs(previousPrice) < PRICE_COMPARISON_EPSILON) return null
        return (currentPrice - previousPrice) / previousPrice * PERCENT_SCALE
    }
}
