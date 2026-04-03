package dev.kigya.pricely.navigation.api

interface Navigator {

    fun navigate(route: String)

    fun navigateBack()
}
