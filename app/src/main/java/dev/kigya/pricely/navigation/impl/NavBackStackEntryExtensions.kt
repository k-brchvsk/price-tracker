package dev.kigya.pricely.navigation.impl

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.requireString(key: String): String {
    return requireNotNull(arguments?.getString(key)) {
        "Required argument '$key' is missing"
    }
}
