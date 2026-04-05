package dev.kigya.pricely.navigation.internal

import androidx.navigation.NavHostController
import dev.kigya.pricely.feature.feed.api.FeedRoute
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AppNavigatorSpec : FunSpec({
    val root = FeedRoute

    test("constructs with root destination") {
        AppNavigator(rootDestination = root).shouldNotBeNull()
    }

    test("navigateBack pops when possible") {
        val nav = mockk<NavHostController>(relaxed = true)
        every { nav.popBackStack() } returns true
        val navigator = AppNavigator(rootDestination = root)
        navigator.attach(nav)
        navigator.navigateBack()
        verify(exactly = 1) { nav.popBackStack() }
    }
})
