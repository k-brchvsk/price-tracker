package dev.kigya.pricely.feature.feed.di

import dev.kigya.pricely.feature.feed.api.FeedFeatureEntryContract
import dev.kigya.pricely.feature.feed.internal.FeedFeatureEntry
import dev.kigya.pricely.feature.feed.internal.FeedViewModel
import dev.kigya.pricely.feature.feed.api.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.feature.feed.api.usecase.StartPriceSessionUseCase
import dev.kigya.pricely.feature.feed.api.usecase.TogglePriceFeedUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val feedFeatureModule = module {
    singleOf(::FeedFeatureEntry) {
        bind<FeedFeatureEntryContract>()
    }
    factoryOf(::ObservePriceSessionUseCase)
    factoryOf(::TogglePriceFeedUseCase)
    factoryOf(::StartPriceSessionUseCase)
    viewModelOf(::FeedViewModel)
}
