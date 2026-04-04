package dev.kigya.pricely.di

import dev.kigya.pricely.data.repository.PriceSessionRepositoryImpl
import dev.kigya.pricely.domain.repository.PriceSessionRepository
import dev.kigya.pricely.domain.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.domain.usecase.ReconnectPriceSessionUseCase
import dev.kigya.pricely.domain.usecase.StartPriceSessionUseCase
import dev.kigya.pricely.ui.main.MainActivityViewModel
import dev.kigya.pricely.ui.symbol.SymbolViewModel
import dev.kigya.pricely.ui.symboldetails.SymbolDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val appModule = module {
    singleOf(::createOkHttpClient)
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    singleOf(::PriceSessionRepositoryImpl) bind PriceSessionRepository::class
    factoryOf(::ObservePriceSessionUseCase)
    factoryOf(::ReconnectPriceSessionUseCase)
    factoryOf(::StartPriceSessionUseCase)
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::SymbolViewModel)
    viewModelOf(::SymbolDetailsViewModel)
}

private fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .build()
