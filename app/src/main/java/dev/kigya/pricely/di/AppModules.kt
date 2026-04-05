package dev.kigya.pricely.di

import android.app.Application
import coil3.SingletonImageLoader
import dev.kigya.pricely.core.pricesession.di.priceSessionModule
import dev.kigya.pricely.feature.feed.api.FeedRoute
import dev.kigya.pricely.feature.feed.di.feedFeatureModule
import dev.kigya.pricely.feature.symboldetails.di.symbolDetailsFeatureModule
import dev.kigya.pricely.navigation.api.NavigatorContract
import dev.kigya.pricely.navigation.internal.AppNavigator
import dev.kigya.pricely.ui.MainActivityViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::PricelySingletonImageLoaderFactory) bind SingletonImageLoader.Factory::class
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

fun Application.installSingletonImageLoaderFromKoin() {
    SingletonImageLoader.setSafe { context ->
        getKoin().get<SingletonImageLoader.Factory>().newImageLoader(context)
    }
}
