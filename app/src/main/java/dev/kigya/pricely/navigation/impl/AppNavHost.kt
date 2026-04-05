package dev.kigya.pricely.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.kigya.pricely.feature.feed.api.FeedFeatureEntryContract
import dev.kigya.pricely.feature.feed.api.FeedRoute
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsFeatureEntryContract
import dev.kigya.pricely.navigation.api.NavigationRoute
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: NavigationRoute = FeedRoute,
) {
    val appNavigator = koinInject<AppNavigator>()
    DisposableEffect(navController) {
        appNavigator.attach(navController)
        onDispose { appNavigator.detach() }
    }
    val feedEntry = koinInject<FeedFeatureEntryContract>()
    val symbolDetailsEntry = koinInject<SymbolDetailsFeatureEntryContract>()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        feedEntry.registerGraph(navGraphBuilder = this)
        symbolDetailsEntry.registerGraph(navGraphBuilder = this)
    }
}
