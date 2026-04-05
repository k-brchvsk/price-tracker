package dev.kigya.pricely.data.websocket

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlin.math.pow

class WebSocketSessionManager(
    private val okHttpClient: OkHttpClient,
    private val scope: CoroutineScope,
) {

    interface EventListener {
        suspend fun onConnected()
        suspend fun onMessage(text: String)
        suspend fun onDisconnected(isFailure: Boolean)
    }

    private val mutex = Mutex()

    private var socket: WebSocket? = null
    private var reconnectJob: Job? = null
    private var reconnectAttempt = 0
    private var lastUrl: String? = null
    private var eventListener: EventListener? = null

    private val wsListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            scope.launch {
                mutex.withLock {
                    if (socket !== webSocket) return@launch
                    reconnectAttempt = 0
                    reconnectJob?.cancel()
                    reconnectJob = null
                }
                eventListener?.onConnected()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            scope.launch {
                val isCurrent = mutex.withLock { socket === webSocket }
                if (isCurrent) eventListener?.onMessage(text)
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSE_CODE, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            scope.launch { handleSocketDown(webSocket, isFailure = false) }
        }

        override fun onFailure(webSocket: WebSocket, throwable: Throwable, response: Response?) {
            scope.launch { handleSocketDown(webSocket, isFailure = true) }
        }
    }

    fun setEventListener(listener: EventListener) {
        eventListener = listener
    }

    suspend fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        mutex.withLock {
            if (socket != null) return
            lastUrl = url
            socket = okHttpClient.newWebSocket(request, wsListener)
        }
    }

    suspend fun reconnect() {
        val previousSocket: WebSocket?
        mutex.withLock {
            reconnectJob?.cancel()
            reconnectJob = null
            reconnectAttempt = 0
            previousSocket = socket
            socket = null
        }
        previousSocket?.close(NORMAL_CLOSE_CODE, "manual reconnect")
        lastUrl?.let { connect(it) }
    }

    suspend fun disconnect() {
        val previousSocket: WebSocket?
        mutex.withLock {
            reconnectJob?.cancel()
            reconnectJob = null
            reconnectAttempt = 0
            previousSocket = socket
            socket = null
        }
        previousSocket?.close(NORMAL_CLOSE_CODE, "manual disconnect")
    }

    suspend fun send(text: String): Boolean {
        val webSocket = mutex.withLock { socket } ?: return false
        return webSocket.send(text)
    }

    private suspend fun handleSocketDown(closedSocket: WebSocket, isFailure: Boolean) {
        mutex.withLock {
            if (socket !== closedSocket) return
            socket = null
        }
        eventListener?.onDisconnected(isFailure)
        if (isFailure) scheduleAutoReconnect()
    }

    private suspend fun scheduleAutoReconnect() {
        val attempt: Int
        mutex.withLock {
            reconnectJob?.cancel()
            attempt = reconnectAttempt
            reconnectJob = scope.launch {
                val delayMs = (
                    BASE_RECONNECT_DELAY_MS *
                        BACKOFF_MULTIPLIER.pow(attempt.coerceAtMost(MAX_RECONNECT_EXPONENT))
                    ).toLong()
                delay(delayMs)
                mutex.withLock { reconnectAttempt++ }
                lastUrl?.let { connect(it) }
            }
        }
    }

    private companion object {
        const val NORMAL_CLOSE_CODE = 1000
        const val BASE_RECONNECT_DELAY_MS = 1_000.0
        const val BACKOFF_MULTIPLIER = 2.0
        const val MAX_RECONNECT_EXPONENT = 5
    }
}
