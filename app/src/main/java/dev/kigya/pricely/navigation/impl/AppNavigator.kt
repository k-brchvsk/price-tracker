package dev.kigya.pricely.navigation.impl

import androidx.navigation.NavHostController
import dev.kigya.pricely.navigation.api.NavigationRoute
import dev.kigya.pricely.navigation.api.NavigatorContract
import java.util.concurrent.atomic.AtomicReference

class AppNavigator(
    private val rootDestination: NavigationRoute,
) : NavigatorContract {

    private val navControllerRef = AtomicReference<NavHostController?>(null)

    fun attach(navController: NavHostController) {
        navControllerRef.set(navController)
    }

    fun detach() {
        navControllerRef.set(null)
    }

    override fun navigateTo(destination: NavigationRoute) {
        navControllerRef.get()?.navigate(destination) {
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateBack() {
        val navController = navControllerRef.get() ?: return
        if (!navController.popBackStack()) {
            navController.navigate(rootDestination) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}
