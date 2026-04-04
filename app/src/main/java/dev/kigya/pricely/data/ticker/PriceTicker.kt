package dev.kigya.pricely.data.ticker

import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.data.mapper.toWireDto
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

class PriceTicker(
    private val scope: CoroutineScope,
    private val json: Json,
) {
    private var job: Job? = null
    private var seq = 0L

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

    fun buildPayload(quotes: Map<String, Quote>): String? {
        val symbol = SymbolCatalog.entries.random().ticker
        val quote = quotes[symbol] ?: return null
        seq++
        val jitter = Random.nextDouble(-MAX_JITTER_FRACTION, MAX_JITTER_FRACTION)
        val newPrice = (quote.currentPrice * (1.0 + jitter)).coerceAtLeast(MIN_PRICE)
        val payload = PriceWirePayload(symbol = symbol, price = newPrice, seq = seq)
        return json.encodeToString(payload.toWireDto())
    }

    companion object {
        private const val TICK_INTERVAL_MS = 2_000L
        private const val MAX_JITTER_FRACTION = 0.02
        private const val MIN_PRICE = 0.01
        private val CRYPTO_TICKERS = setOf("BTC", "ETH", "SOL")
        private const val CRYPTO_MIN = 50_000.0
        private const val CRYPTO_MAX = 120_000.0
        private const val STOCK_MIN = 30.0
        private const val STOCK_MAX = 500.0

        fun seedQuotes(): Map<String, Quote> {
            val rng = Random(System.nanoTime())
            return SymbolCatalog.entries.associate { meta ->
                val base = if (meta.ticker in CRYPTO_TICKERS) {
                    rng.nextDouble(CRYPTO_MIN, CRYPTO_MAX)
                } else {
                    rng.nextDouble(STOCK_MIN, STOCK_MAX)
                }
                meta.ticker to Quote(
                    symbol = meta.ticker,
                    currentPrice = base,
                    previousPrice = base,
                    trend = Trend.Neutral,
                    lastSeq = 0L,
                )
            }
        }
    }
}
