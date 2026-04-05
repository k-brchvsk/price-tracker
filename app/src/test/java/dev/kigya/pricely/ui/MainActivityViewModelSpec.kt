package dev.kigya.pricely.ui

import android.content.Intent
import dev.kigya.pricely.domain.repository.PriceSessionControllerRepository
import dev.kigya.pricely.feature.feed.api.usecase.StartPriceSessionUseCase
import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.mockk
import io.mockk.verify

class MainActivityViewModelSpec : FunSpec({
    test("onActivityCreate starts price session") {
        val controller = mockk<PriceSessionControllerRepository>(relaxed = true)
        val start = StartPriceSessionUseCase(controller)
        val vm = MainActivityViewModel(start)
        vm.onActivityCreate()
        verify(exactly = 1) { controller.startSession() }
    }

    test("onNewIntent emits intent on deep link flow") {
        val controller = mockk<PriceSessionControllerRepository>(relaxed = true)
        val start = StartPriceSessionUseCase(controller)
        val vm = MainActivityViewModel(start)
        val intent = Intent("dev.kigya.pricely.TEST_DEEP_LINK")
        vm.deepLinkIntents.test {
            vm.onNewIntent(intent)
            awaitItem() shouldBeSameInstanceAs intent
        }
    }
})
