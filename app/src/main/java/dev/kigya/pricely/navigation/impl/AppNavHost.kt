package dev.kigya.pricely.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import dev.kigya.pricely.navigation.api.DeepLinks
import dev.kigya.pricely.navigation.destinations.StockDetailsDestination
import dev.kigya.pricely.navigation.destinations.StockListDestination
import dev.kigya.pricely.ui.stockdetails.StockDetailsScreen
import dev.kigya.pricely.ui.stocklist.StockListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = StockListDestination.route,
) {
    val navigator = rememberNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(StockListDestination.route) {
            StockListScreen(
                onStockClick = { symbol ->
                    navigator.navigate(StockDetailsDestination.createRoute(symbol))
                },
            )
        }

        composable(
            route = StockDetailsDestination.route,
            arguments = listOf(
                navArgument(StockDetailsDestination.ARG_SYMBOL) {
                    type = NavType.StringType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.STOCK_DETAILS
                },
            ),
        ) { backStackEntry ->
            val symbol = backStackEntry.requireString(StockDetailsDestination.ARG_SYMBOL)
            StockDetailsScreen(
                symbol = symbol,
                onBackClick = {
                    navigator.navigateBack()
                },
            )
        }
    }
}
