package dev.kigya.pricely.feature.symboldetails.internal

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsFeatureEntryContract
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsRoute

class SymbolDetailsFeatureEntry : SymbolDetailsFeatureEntryContract {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<SymbolDetailsRoute>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "stocks://symbol/{symbol}"
                },
            ),
        ) {
            SymbolDetailsScreen()
        }
    }
}
