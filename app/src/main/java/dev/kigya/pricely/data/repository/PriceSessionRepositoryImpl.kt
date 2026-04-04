package dev.kigya.pricely.data.repository

import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.data.mapper.toDomain
import dev.kigya.pricely.data.ticker.PriceTicker
import dev.kigya.pricely.data.websocket.WebSocketSessionManager
import dev.kigya.pricely.domain.logic.PriceQuoteMerge
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.repository.PriceSessionController
import dev.kigya.pricely.domain.repository.PriceSessionStateSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

class PriceSessionRepositoryImpl(
    private val socketManager: WebSocketSessionManager,
    private val ticker: PriceTicker,
    private val appScope: CoroutineScope,
    private val json: Json,
) : PriceSessionStateSource, PriceSessionController {

    private val mutex = Mutex()

    private val _sessionState = MutableStateFlow(PriceSessionState())
    override val state: StateFlow<PriceSessionState> = _sessionState.asStateFlow()

    private var sessionStarted = false

    init {
        socketManager.setEventListener(
            object : WebSocketSessionManager.EventListener {
                override suspend fun onConnected() = handleConnected()
                override suspend fun onMessage(text: String) = handleEcho(text)
                override suspend fun onDisconnected(isFailure: Boolean) = handleDisconnected(isFailure)
            },
        )
    }

    override fun startSession() {
        appScope.launch {
            mutex.withLock {
                if (sessionStarted) return@launch
                sessionStarted = true
            }
            ensureSeeded()
            socketManager.connect(WS_URL)
        }
    }

    override fun reconnect() {
        appScope.launch {
            mutex.withLock {
                ticker.stop()
                _sessionState.value = _sessionState.value.copy(
                    isStreamConnected = false,
                    isInitialConnectFailure = false,
                )
            }
            socketManager.reconnect()
        }
    }

    private suspend fun handleConnected() {
        mutex.withLock {
            ticker.stop()
            ticker.start { sendTick() }
            _sessionState.value = _sessionState.value.copy(
                initialConnectionSettled = true,
                isStreamConnected = true,
                isInitialConnectFailure = false,
            )
        }
    }

    private suspend fun handleDisconnected(isFailure: Boolean) {
        mutex.withLock {
            ticker.stop()
            val s = _sessionState.value
            _sessionState.value = s.copy(
                initialConnectionSettled = true,
                isStreamConnected = false,
                isInitialConnectFailure = !s.hasEverReceivedValidEcho && isFailure,
            )
        }
    }

    private suspend fun handleEcho(text: String) {
        val dto = try {
            json.decodeFromString<PriceWireDto>(text)
        } catch (_: Exception) {
            return
        }
        val payload = dto.toDomain()
        if (!SymbolCatalog.isKnownTicker(payload.symbol)) return

        mutex.withLock {
            val s = _sessionState.value
            val merged = PriceQuoteMerge.mergeEcho(
                quotes = s.quotesBySymbol,
                payload = payload,
            ) ?: return@withLock
            _sessionState.value = s.copy(
                quotesBySymbol = merged,
                sortedQuotes = PriceQuoteMerge.sortQuotes(merged),
                hasEverReceivedValidEcho = true,
                isInitialConnectFailure = false,
            )
        }
    }

    private suspend fun sendTick() {
        var payload: String? = null
        mutex.withLock {
            val state = _sessionState.value
            if (!state.isStreamConnected) return
            payload = ticker.buildPayload(state.quotesBySymbol)
        }
        payload?.let { socketManager.send(it) }
    }

    private suspend fun ensureSeeded() {
        mutex.withLock {
            if (_sessionState.value.quotesBySymbol.isNotEmpty()) return@withLock
            _sessionState.value = _sessionState.value.copy(
                quotesBySymbol = PriceTicker.seedQuotes(),
                sortedQuotes = emptyList(),
                hasEverReceivedValidEcho = false,
            )
        }
    }

    private companion object {
        const val WS_URL = "wss://ws.postman-echo.com/raw"
    }
}
