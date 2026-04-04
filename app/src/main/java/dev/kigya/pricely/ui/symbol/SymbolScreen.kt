package dev.kigya.pricely.ui.symbol

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.ui.model.FeedUiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolScreen(
    onSymbolClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SymbolViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Pricely") },
                actions = {
                    Text(
                        text = state.connectionLabel,
                        modifier = Modifier.padding(end = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    TextButton(onClick = { viewModel.onReconnectClicked() }) {
                        Text("Reconnect")
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val s = state) {
                is FeedUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is FeedUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Connection interrupted",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                is FeedUiState.Content -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = s.quotes,
                            key = { it.symbol },
                        ) { quote ->
                            ListItem(
                                headlineContent = {
                                    Text(quote.symbol, fontWeight = FontWeight.Bold)
                                },
                                supportingContent = {
                                    Text(
                                        "${quote.formattedPrice}  ${quote.formattedPercentChange}  ${quote.trend.name}",
                                        color = if (s.isConnected) {
                                            MaterialTheme.colorScheme.onSurface
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        },
                                    )
                                },
                                modifier = Modifier.clickable { onSymbolClick(quote.symbol) },
                            )
                        }
                    }
                }
            }
        }
    }
}
