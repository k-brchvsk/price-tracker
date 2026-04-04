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
import dev.kigya.pricely.navigation.destinations.SymbolDestination
import dev.kigya.pricely.navigation.destinations.SymbolDetailsDestination
import dev.kigya.pricely.ui.symbol.SymbolScreen
import dev.kigya.pricely.ui.symboldetails.SymbolDetailsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = SymbolDestination.route,
) {
    val navigator = rememberNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(SymbolDestination.route) {
            SymbolScreen(
                onSymbolClick = { symbol ->
                    navigator.navigate(SymbolDetailsDestination.createRoute(symbol))
                },
            )
        }

        composable(
            route = SymbolDetailsDestination.route,
            arguments = listOf(
                navArgument(SymbolDetailsDestination.ARG_SYMBOL) {
                    type = NavType.StringType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.STOCK_DETAILS
                },
            ),
        ) {
            SymbolDetailsScreen(
                onBackClick = { navigator.navigateBack() },
            )
        }
    }
}
