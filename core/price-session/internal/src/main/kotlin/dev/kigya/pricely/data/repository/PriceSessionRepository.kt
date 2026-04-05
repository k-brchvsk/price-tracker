package dev.kigya.pricely.data.repository

import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.data.mapper.toDomain
import dev.kigya.pricely.data.ticker.PriceTicker
import dev.kigya.pricely.data.websocket.WebSocketSessionManager
import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.logic.PriceQuoteMerge
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.repository.PriceSessionControllerRepository
import dev.kigya.pricely.domain.repository.PriceSessionStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

class PriceSessionRepository(
    private val socketManager: WebSocketSessionManager,
    private val ticker: PriceTicker,
    private val appScope: CoroutineScope,
    private val json: Json,
) : PriceSessionStateRepository, PriceSessionControllerRepository {

    private val mutex = Mutex()

    private val _sessionState = MutableStateFlow(PriceSessionState())
    override val state: StateFlow<PriceSessionState> = _sessionState.asStateFlow()

    private var sessionStarted = false
    private var isManualDisconnect = false

    init {
        socketManager.setEventListener(
            object : WebSocketSessionManager.EventListener {
                override suspend fun onConnected() = handleConnected()
                override suspend fun onMessage(text: String) = handleEcho(text)
                override suspend fun onDisconnected(isFailure: Boolean) =
                    handleDisconnected(isFailure)
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

    override fun toggleFeed() {
        appScope.launch {
            val wasConnected = mutex.withLock { _sessionState.value.isStreamConnected }
            if (wasConnected) stopFeed() else startFeed()
        }
    }

    private suspend fun stopFeed() {
        isManualDisconnect = true
        mutex.withLock {
            ticker.stop()
            _sessionState.value = _sessionState.value.copy(isStreamConnected = false)
        }
        socketManager.disconnect()
    }

    private suspend fun startFeed() {
        isManualDisconnect = false
        mutex.withLock {
            _sessionState.value = _sessionState.value.copy(
                isStreamConnected = false,
            )
        }
        socketManager.reconnect()
    }

    private suspend fun handleConnected() {
        mutex.withLock {
            ticker.stop()
            ticker.start { sendTick() }
            _sessionState.value = _sessionState.value.copy(
                isInitialConnectionSettled = true,
                isStreamConnected = true,
                isInitialConnectFailure = false,
            )
        }
    }

    private suspend fun handleDisconnected(isFailure: Boolean) {
        mutex.withLock {
            ticker.stop()
            val sessionState = _sessionState.value
            val shouldMarkAsFailure = !isManualDisconnect && !sessionState.hasEverReceivedValidEcho && isFailure
            _sessionState.value = sessionState.copy(
                isInitialConnectionSettled = true,
                isStreamConnected = false,
                isInitialConnectFailure = shouldMarkAsFailure,
            )
            isManualDisconnect = false
        }
    }

    private suspend fun handleEcho(text: String) {
        val priceWireDto = try {
            json.decodeFromString<PriceWireDto>(text)
        } catch (_: Exception) {
            return
        }
        val payload = priceWireDto.toDomain()
        if (!SymbolCatalog.isKnownTicker(payload.symbol)) return

        mutex.withLock {
            val sessionState = _sessionState.value
            val merged = PriceQuoteMerge.mergeEcho(
                quotes = sessionState.quotesBySymbol,
                payload = payload,
            ) ?: return@withLock
            _sessionState.value = sessionState.copy(
                quotesBySymbol = merged,
                sortedQuotes = PriceQuoteMerge.sortQuotes(merged),
                hasEverReceivedValidEcho = true,
                isInitialConnectFailure = false,
            )
        }
    }

    private suspend fun sendTick() {
        val payloads = mutex.withLock {
            val state = _sessionState.value
            if (!state.isStreamConnected) return
            ticker.buildTickPayloads(state.quotesBySymbol)
        }
        payloads.forEach { socketManager.send(it) }
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
}

private const val WS_URL = "wss://ws.postman-echo.com/raw"
