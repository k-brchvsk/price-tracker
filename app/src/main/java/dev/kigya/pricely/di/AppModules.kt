package dev.kigya.pricely.di

import dev.kigya.pricely.core.pricesession.di.priceSessionModule
import dev.kigya.pricely.feature.feed.api.FeedRoute
import dev.kigya.pricely.feature.feed.di.feedFeatureModule
import dev.kigya.pricely.feature.symboldetails.di.symbolDetailsFeatureModule
import dev.kigya.pricely.navigation.api.NavigatorContract
import dev.kigya.pricely.navigation.impl.AppNavigator
import dev.kigya.pricely.ui.main.MainActivityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { AppNavigator(rootDestination = FeedRoute) }
    single<NavigatorContract> { get<AppNavigator>() }
    viewModelOf(::MainActivityViewModel)
}

val appModules = listOf(
    priceSessionModule,
    appModule,
    feedFeatureModule,
    symbolDetailsFeatureModule,
)
