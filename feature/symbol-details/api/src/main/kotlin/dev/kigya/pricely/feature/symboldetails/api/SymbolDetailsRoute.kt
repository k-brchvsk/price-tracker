package dev.kigya.pricely.feature.symboldetails.api

import dev.kigya.pricely.navigation.api.NavigationRoute
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolDetailsRoute(
    @SerialName("symbol")
    val symbol: String,
) : NavigationRoute
