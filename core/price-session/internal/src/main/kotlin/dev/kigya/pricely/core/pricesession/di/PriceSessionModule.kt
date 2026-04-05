package dev.kigya.pricely.core.pricesession.di

import dev.kigya.pricely.data.repository.PriceSessionRepository
import dev.kigya.pricely.data.ticker.PriceTicker
import dev.kigya.pricely.data.websocket.WebSocketSessionManager
import dev.kigya.pricely.domain.repository.PriceSessionControllerRepository
import dev.kigya.pricely.domain.repository.PriceSessionStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose
import java.util.concurrent.TimeUnit

val priceSessionModule = module {
    singleOf(::createOkHttpClient)
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    single { appScope }
    single { Json { ignoreUnknownKeys = true } }

    singleOf(::WebSocketSessionManager)
    singleOf(::PriceTicker)
    singleOf(::PriceSessionRepository) {
        bind<PriceSessionStateRepository>()
        bind<PriceSessionControllerRepository>()
    }.onClose { appScope.cancel() }
}

private fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .build()
