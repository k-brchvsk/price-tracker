package dev.kigya.pricely.domain.logic

import kotlin.math.abs

object QuotePriceMath {

    private const val PRICE_COMPARISON_EPSILON = 1e-9

    fun percentChange(previousPrice: Double, currentPrice: Double): Double? {
        if (abs(previousPrice) < PRICE_COMPARISON_EPSILON) return null
        return ((currentPrice - previousPrice) / previousPrice) * 100.0
    }
}
