package dev.kigya.pricely.navigation.impl

import androidx.navigation.NavHostController
import dev.kigya.pricely.navigation.api.Navigator
import dev.kigya.pricely.navigation.destinations.StockListDestination

class NavigatorImpl(
    private val navController: NavHostController,
) : Navigator {

    override fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateBack() {
        if (!navController.popBackStack()) {
            navController.navigate(StockListDestination.route)
        }
    }
}
