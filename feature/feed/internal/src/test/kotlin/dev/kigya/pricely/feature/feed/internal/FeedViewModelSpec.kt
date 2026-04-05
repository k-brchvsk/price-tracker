package dev.kigya.pricely.feature.feed.internal

import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.repository.PriceSessionControllerRepository
import dev.kigya.pricely.domain.repository.PriceSessionStateRepository
import dev.kigya.pricely.feature.feed.api.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.feature.feed.api.usecase.TogglePriceFeedUseCase
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsRoute
import dev.kigya.pricely.navigation.api.NavigatorContract
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelSpec : FunSpec({
    isolationMode = IsolationMode.InstancePerTest

    test("onToggleFeedClick invokes use case") {
        runTest {
            val mainDispatcher = StandardTestDispatcher(testScheduler)
            Dispatchers.setMain(mainDispatcher)
            try {
                val session = MutableStateFlow(PriceSessionState())
                val stateRepo = mockk<PriceSessionStateRepository> { every { state } returns session }
                val observe = ObservePriceSessionUseCase(stateRepo)
                val controller = mockk<PriceSessionControllerRepository>(relaxed = true)
                val toggle = TogglePriceFeedUseCase(controller)
                val navigator = mockk<NavigatorContract>(relaxed = true)
                val vm = FeedViewModel(observe, toggle, navigator)
                vm.onToggleFeedClick()
                verify(exactly = 1) { controller.toggleFeed() }
            } finally {
                Dispatchers.resetMain()
            }
        }
    }

    test("onSymbolClicked navigates to details") {
        runTest {
            val mainDispatcher = StandardTestDispatcher(testScheduler)
            Dispatchers.setMain(mainDispatcher)
            try {
                val session = MutableStateFlow(PriceSessionState())
                val stateRepo = mockk<PriceSessionStateRepository> { every { state } returns session }
                val observe = ObservePriceSessionUseCase(stateRepo)
                val controller = mockk<PriceSessionControllerRepository>(relaxed = true)
                val toggle = TogglePriceFeedUseCase(controller)
                val navigator = mockk<NavigatorContract>(relaxed = true)
                val vm = FeedViewModel(observe, toggle, navigator)
                vm.onSymbolClicked("META")
                verify(exactly = 1) { navigator.navigateTo(SymbolDetailsRoute(symbol = "META")) }
            } finally {
                Dispatchers.resetMain()
            }
        }
    }
})
