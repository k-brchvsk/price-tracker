package dev.kigya.pricely.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kigya.pricely.core.designsystem.theme.PricelyTheme
import dev.kigya.pricely.navigation.impl.AppNavHost
import kotlinx.coroutines.flow.SharedFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.onActivityCreate()
        enableEdgeToEdge()
        setContent {
            PricelyTheme {
                AppNavigation(
                    deepLinkIntents = mainViewModel.deepLinkIntents,
                    consumeInitialDeepLink = savedInstanceState == null,
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mainViewModel.onNewIntent(intent)
    }
}

@Composable
private fun AppNavigation(
    deepLinkIntents: SharedFlow<Intent>,
    consumeInitialDeepLink: Boolean,
) {
    val navController = rememberNavController()
    val activity = LocalContext.current as ComponentActivity
    DeepLinkEffect(navController, deepLinkIntents)
    AppNavHost(navController = navController)
    LaunchedEffect(consumeInitialDeepLink) {
        if (consumeInitialDeepLink) {
            navController.handleDeepLink(activity.intent)
        }
    }
}

@Composable
private fun DeepLinkEffect(
    navController: NavHostController,
    intents: SharedFlow<Intent>,
) {
    LaunchedEffect(Unit) {
        intents.collect { intent ->
            navController.handleDeepLink(intent)
        }
    }
}
