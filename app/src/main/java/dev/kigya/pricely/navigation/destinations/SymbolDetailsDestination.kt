package dev.kigya.pricely.navigation.destinations

object SymbolDetailsDestination {
    const val ARG_SYMBOL = "symbol"
    const val route = "symbol_details/{$ARG_SYMBOL}"

    fun createRoute(symbol: String): String = "symbol_details/$symbol"
}
