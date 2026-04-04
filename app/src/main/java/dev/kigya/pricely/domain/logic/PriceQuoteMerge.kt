package dev.kigya.pricely.domain.logic

import dev.kigya.pricely.domain.model.PriceWirePayload
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import kotlin.math.abs

object PriceQuoteMerge {

    private const val EPS = 1e-9

    fun trendFrom(previous: Double, next: Double): Trend = when {
        next > previous + EPS -> Trend.Up
        next < previous - EPS -> Trend.Down
        else -> Trend.Neutral
    }

    fun mergeEcho(
        quotes: Map<String, Quote>,
        symbol: String,
        echoedPrice: Double,
        incomingSeq: Long,
    ): Map<String, Quote>? {
        val existing = quotes[symbol] ?: return null
        if (incomingSeq <= existing.lastSeq) return null
        return quotes + (symbol to existing.withEchoedPrice(echoedPrice, incomingSeq))
    }

    private fun Quote.withEchoedPrice(echoedPrice: Double, incomingSeq: Long): Quote =
        copy(
            previousPrice = currentPrice,
            currentPrice = echoedPrice,
            trend = trendFrom(currentPrice, echoedPrice),
            lastSeq = incomingSeq,
        )

    fun mergeEcho(quotes: Map<String, Quote>, payload: PriceWirePayload): Map<String, Quote>? =
        mergeEcho(quotes, payload.symbol, payload.price, payload.seq)

    fun sortQuotes(quotes: Map<String, Quote>): List<Quote> =
        quotes.values.sortedWith(
            compareByDescending<Quote> { it.currentPrice }.thenBy { it.symbol },
        )

    fun percentChange(previousPrice: Double, currentPrice: Double): Double? {
        if (abs(previousPrice) < EPS) return null
        return ((currentPrice - previousPrice) / previousPrice) * 100.0
    }
}