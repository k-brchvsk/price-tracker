package dev.kigya.pricely.feature.symboldetails.di

import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsFeatureEntryContract
import dev.kigya.pricely.feature.symboldetails.api.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.feature.symboldetails.internal.SymbolDetailsFeatureEntry
import dev.kigya.pricely.feature.symboldetails.internal.SymbolDetailsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val symbolDetailsFeatureModule = module {
    singleOf(::SymbolDetailsFeatureEntry) {
        bind<SymbolDetailsFeatureEntryContract>()
    }
    factoryOf(::ObservePriceSessionUseCase)
    viewModelOf(::SymbolDetailsViewModel)
}
