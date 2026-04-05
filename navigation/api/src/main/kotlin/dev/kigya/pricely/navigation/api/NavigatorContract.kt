package dev.kigya.pricely.navigation.api

interface NavigatorContract {

    fun navigateTo(destination: NavigationRoute)

    fun navigateBack()
}
