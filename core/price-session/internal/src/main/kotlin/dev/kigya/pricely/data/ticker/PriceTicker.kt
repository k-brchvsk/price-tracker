package dev.kigya.pricely.data.ticker

import dev.kigya.pricely.data.mapper.toWireDto
import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.model.PriceWirePayload
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.random.Random

internal class PriceTicker(
    private val scope: CoroutineScope,
    private val json: Json,
) {
    private var job: Job? = null
    private var sequenceNumber = 0L

    fun start(onTick: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            while (isActive) {
                delay(TICK_INTERVAL_MS)
                onTick()
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    fun buildTickPayloads(quotes: Map<String, Quote>): List<String> {
        if (quotes.isEmpty()) return emptyList()
        val symbols = SymbolCatalog.entries
            .map { it.ticker }
            .filter { it in quotes }
            .shuffled()
            .take(SYMBOLS_PER_TICK)
        return symbols.mapNotNull { symbol ->
            val quote = quotes[symbol] ?: return@mapNotNull null
            sequenceNumber++
            val jitter = Random.nextDouble(-MAX_JITTER_FRACTION, MAX_JITTER_FRACTION)
            val newPrice = (quote.currentPrice * (1.0 + jitter)).coerceAtLeast(MIN_PRICE)
            val payload = PriceWirePayload(symbol = symbol, price = newPrice, sequenceNumber = sequenceNumber)
            json.encodeToString(payload.toWireDto())
        }
    }

    companion object {
        fun seedQuotes(): Map<String, Quote> {
            val random = Random(System.nanoTime())
            return SymbolCatalog.entries.associate { catalogEntry ->
                val base = if (catalogEntry.ticker in CRYPTO_TICKERS) {
                    random.nextDouble(CRYPTO_MIN, CRYPTO_MAX)
                } else {
                    random.nextDouble(STOCK_MIN, STOCK_MAX)
                }
                catalogEntry.ticker to Quote(
                    symbol = catalogEntry.ticker,
                    currentPrice = base,
                    previousPrice = base,
                    trend = Trend.Neutral,
                    lastSequenceNumber = 0L,
                )
            }
        }
    }
}

private const val TICK_INTERVAL_MS = 2_000L
private const val SYMBOLS_PER_TICK = 5
private const val MAX_JITTER_FRACTION = 0.07
private const val MIN_PRICE = 0.01
private val CRYPTO_TICKERS = setOf("BTC", "ETH", "SOL")
private const val CRYPTO_MIN = 50_000.0
private const val CRYPTO_MAX = 120_000.0
private const val STOCK_MIN = 30.0
private const val STOCK_MAX = 500.0
