package dev.kigya.pricely.ui.symbol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionButton
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.state.PricelyStateView
import dev.kigya.pricely.core.designsystem.components.stock.PricelyStockItem
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionStatus
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.components.topbar.PricelyTopBar
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.ui.mapper.symbolIconDrawableRes
import dev.kigya.pricely.ui.mapper.toPricelyActionState
import dev.kigya.pricely.ui.mapper.toPricelyConnectionState
import dev.kigya.pricely.ui.mapper.toPricelyTrend
import dev.kigya.pricely.ui.model.FeedUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SymbolScreen(
    onSymbolClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SymbolViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.background,
        topBar = {
            PricelyTopBar(
                startContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
                    ) {
                        PricelyIcon(
                            painter = painterResource(R.drawable.ic_icon_logo),
                            tint = AppTheme.colors.primary,
                            contentDescription = null,
                        )

                        PricelyText(
                            text = stringResource(R.string.topbar_app_name),
                            style = AppTheme.typography.headingMedium,
                        )
                    }
                },
                endContent = {
                    PricelyConnectionStatus(state.toPricelyConnectionState())

                    PricelyActionButton(
                        state = state.toPricelyActionState(),
                        icon = painterResource(R.drawable.ic_swap),
                        contentDescription = stringResource(R.string.topbar_reconnect),
                        onClick = { viewModel.onReconnectClicked() },
                    )
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
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppTheme.colors.loadingIndicatorActive,
                        trackColor = AppTheme.colors.loadingIndicatorTrack,
                    )
                }

                is FeedUiState.Error -> {
                    PricelyStateView(
                        icon = painterResource(R.drawable.ic_sync),
                        title = stringResource(R.string.feed_error_title),
                        description = stringResource(R.string.feed_error_description),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is FeedUiState.Content -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = s.quotes,
                            key = { it.symbol },
                        ) { quote ->
                            PricelyStockItem(
                                icon = painterResource(quote.symbol.symbolIconDrawableRes()),
                                ticker = quote.symbol,
                                name = quote.companyName,
                                price = quote.formattedPrice,
                                changePercent = quote.formattedPercentChange,
                                trend = quote.trend.toPricelyTrend(),
                                isMuted = !s.isConnected,
                                onClick = { onSymbolClick(quote.symbol) },
                            )
                        }
                    }
                }
            }
        }
    }
}
