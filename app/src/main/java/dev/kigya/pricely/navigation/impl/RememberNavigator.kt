package dev.kigya.pricely.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import dev.kigya.pricely.navigation.api.Navigator

@Composable
fun rememberNavigator(navController: NavHostController): Navigator {
    return remember(navController) {
        NavigatorImpl(navController)
    }
}
