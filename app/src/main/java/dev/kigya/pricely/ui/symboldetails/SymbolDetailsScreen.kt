package dev.kigya.pricely.ui.symboldetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.domain.model.Trend
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolDetailsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SymbolDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Symbol Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            when {
                state.unknownSymbol -> {
                    Text("Unknown symbol", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "N/A",
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                state.isLoading -> {
                    Text("Loading…", style = MaterialTheme.typography.bodyLarge)
                }

                else -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                state.symbol,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            state.companyName?.let {
                                Text(
                                    it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp),
                                )
                            }
                            Text(
                                text = state.formattedPrice,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                            Text(
                                text = state.formattedPercentChange,
                                color = when {
                                    !state.showTrendIndicators -> MaterialTheme.colorScheme.onSurface
                                    state.trend == Trend.Up -> AppTheme.colors.trendUp
                                    state.trend == Trend.Down -> AppTheme.colors.trendDown
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                    state.description?.let { desc ->
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp),
                        )
                    }
                }
            }
        }
    }
}
