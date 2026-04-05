package dev.kigya.pricely.data.repository

import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.data.ticker.PriceTicker
import dev.kigya.pricely.data.websocket.WebSocketSessionManager
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PriceSessionRepositorySpec : FunSpec({
    val json = Json { ignoreUnknownKeys = true }

    fun createRepo(
        socket: WebSocketSessionManager,
        dispatcher: CoroutineDispatcher,
    ): Pair<PriceSessionRepository, WebSocketSessionManager.EventListener> {
        val listeners = mutableListOf<WebSocketSessionManager.EventListener>()
        every { socket.setEventListener(any()) } answers { listeners.add(firstArg()) }
        val scope = CoroutineScope(dispatcher + SupervisorJob())
        val ticker = PriceTicker(scope, json, Random(0L))
        val repo = PriceSessionRepository(socket, ticker, scope, json)
        return repo to listeners.single()
    }

    test("startSession connects websocket after seeding") {
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val socket = mockk<WebSocketSessionManager>(relaxed = true)
            val (repo, _) = createRepo(socket, dispatcher)
            repo.startSession()
            advanceUntilIdle()
            coVerify { socket.connect(match { it.startsWith("wss://") }) }
            repo.state.value.quotesBySymbol.isEmpty().shouldBeFalse()
        }
    }

    test("handleEcho merges known symbol price updates") {
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val socket = mockk<WebSocketSessionManager>(relaxed = true)
            val (repo, listener) = createRepo(socket, dispatcher)
            repo.startSession()
            advanceUntilIdle()
            val wire = PriceWireDto(symbol = "AAPL", price = 222.0, sequenceNumber = 1L)
            val text = json.encodeToString(PriceWireDto.serializer(), wire)
            listener.onMessage(text)
            advanceUntilIdle()
            repo.state.value.quotesBySymbol["AAPL"]!!.currentPrice shouldBe 222.0
            repo.state.value.hasEverReceivedValidEcho.shouldBeTrue()
        }
    }

    test("marks initial connect failure when disconnected with failure before any echo") {
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val socket = mockk<WebSocketSessionManager>(relaxed = true)
            val (repo, listener) = createRepo(socket, dispatcher)
            repo.startSession()
            advanceUntilIdle()
            listener.onDisconnected(isFailure = true)
            advanceUntilIdle()
            val s = repo.state.value
            s.isInitialConnectFailure.shouldBeTrue()
            s.hasEverReceivedValidEcho.shouldBeFalse()
        }
    }
})
