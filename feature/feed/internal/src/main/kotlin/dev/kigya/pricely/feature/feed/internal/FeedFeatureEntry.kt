package dev.kigya.pricely.feature.feed.internal

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.kigya.pricely.feature.feed.api.FeedFeatureEntryContract
import dev.kigya.pricely.feature.feed.api.FeedRoute

class FeedFeatureEntry : FeedFeatureEntryContract {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeedRoute> {
            FeedScreen()
        }
    }
}
