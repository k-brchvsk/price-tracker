package dev.kigya.pricely.domain.logic

import dev.kigya.pricely.domain.model.PriceWirePayload
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend

object PriceQuoteMerge {

    private const val PRICE_COMPARISON_EPSILON = 1e-9

    fun trendFrom(
        previous: Double,
        next: Double,
    ): Trend = when {
        next > previous + PRICE_COMPARISON_EPSILON -> Trend.Up
        next < previous - PRICE_COMPARISON_EPSILON -> Trend.Down
        else -> Trend.Neutral
    }

    fun mergeEcho(
        quotes: Map<String, Quote>,
        symbol: String,
        echoedPrice: Double,
        incomingSequenceNumber: Long,
    ): Map<String, Quote>? {
        val existing = quotes[symbol] ?: return null
        if (incomingSequenceNumber <= existing.lastSequenceNumber) return null
        return quotes + (symbol to existing.withEchoedPrice(echoedPrice, incomingSequenceNumber))
    }

    private fun Quote.withEchoedPrice(
        echoedPrice: Double,
        incomingSequenceNumber: Long,
    ): Quote = copy(
        previousPrice = currentPrice,
        currentPrice = echoedPrice,
        trend = trendFrom(currentPrice, echoedPrice),
        lastSequenceNumber = incomingSequenceNumber,
    )

    fun mergeEcho(
        quotes: Map<String, Quote>,
        payload: PriceWirePayload,
    ): Map<String, Quote>? =
        mergeEcho(quotes, payload.symbol, payload.price, payload.sequenceNumber)

    fun sortQuotes(quotes: Map<String, Quote>): List<Quote> = quotes.values.sortedWith(
        compareByDescending<Quote> { it.currentPrice }.thenBy { it.symbol },
    )

    fun percentChange(
        previousPrice: Double,
        currentPrice: Double,
    ): Double? = QuotePriceMath.percentChange(previousPrice, currentPrice)
}
