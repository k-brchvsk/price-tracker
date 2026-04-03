package dev.kigya.pricely.navigation.destinations

object StockDetailsDestination {
    const val ARG_SYMBOL = "symbol"
    const val route = "stock_details/{$ARG_SYMBOL}"

    fun createRoute(symbol: String): String = "stock_details/$symbol"
}
