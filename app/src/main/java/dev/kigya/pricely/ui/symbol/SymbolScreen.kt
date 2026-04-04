package dev.kigya.pricely.ui.symbol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionButton
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIconVariant
import dev.kigya.pricely.core.designsystem.components.state.PricelyStateView
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionStatus
import dev.kigya.pricely.core.designsystem.components.stock.PricelyStockItem
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.components.topbar.PricelyTopBar
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.ui.mapper.symbolIconDrawableRes
import dev.kigya.pricely.ui.mapper.toPricelyActionState
import dev.kigya.pricely.ui.mapper.toPricelyConnectionState
import dev.kigya.pricely.ui.mapper.toPricelyTrend
import dev.kigya.pricely.ui.model.FeedUiState
import dev.kigya.pricely.ui.model.QuoteUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SymbolScreen(
    onSymbolClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SymbolViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SymbolScreenContent(
        state = state,
        modifier = modifier,
        onSymbolClick = onSymbolClick,
        onToggleFeedClicked = { viewModel.onToggleFeedClicked() },
    )
}

@Composable
private fun SymbolScreenContent(
    state: FeedUiState,
    modifier: Modifier,
    onSymbolClick: (String) -> Unit,
    onToggleFeedClicked: () -> Unit,
) {
    val cachedQuotes = remember { mutableStateOf<List<QuoteUiModel>>(emptyList()) }
    var showErrorOverlay by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        when (state) {
            is FeedUiState.Content -> {
                cachedQuotes.value = state.quotes
                showErrorOverlay = false
            }
            is FeedUiState.Loading -> {
                showErrorOverlay = false
            }
            is FeedUiState.Error -> {
                showErrorOverlay = true
            }
        }
    }

    val displayQuotes = if (state is FeedUiState.Content) {
        state.quotes
    } else {
        cachedQuotes.value
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {
        val connectionState = state.toPricelyConnectionState()

        PricelyTopBar(
            startContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
                ) {
                    PricelyIcon(
                        painter = painterResource(R.drawable.ic_icon_logo),
                        contentDescription = null,
                        variant = PricelyIconVariant.PLAIN,
                        tint = AppTheme.colors.primary,
                    )
                    PricelyText(
                        text = stringResource(R.string.topbar_app_name),
                        style = AppTheme.typography.headingMedium,
                    )
                }
            },
            endContent = {
                PricelyConnectionStatus(connectionState)

                PricelyActionButton(
                    state = state.toPricelyActionState(),
                    icon = painterResource(
                        if (state.isConnected) R.drawable.ic_stop else R.drawable.ic_play,
                    ),
                    contentDescription = stringResource(
                        if (state.isConnected) R.string.cd_action_button_stop else R.string.cd_action_button_start,
                    ),
                    onClick = onToggleFeedClicked,
                )
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (displayQuotes.isNotEmpty()) {
                Column {
                    PricelyText(
                        text = stringResource(R.string.stock_list_title),
                        style = AppTheme.typography.headingMedium,
                        modifier = Modifier.padding(
                            horizontal = AppTheme.dimens.space16,
                            vertical = AppTheme.dimens.space8,
                        ),
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = AppTheme.dimens.space16),
                    ) {
                        itemsIndexed(
                            items = displayQuotes,
                            key = { _, item -> item.symbol },
                        ) { _, quote ->
                            PricelyStockItem(
                                icon = painterResource(quote.symbol.symbolIconDrawableRes()),
                                ticker = quote.symbol,
                                name = quote.companyName,
                                price = quote.formattedPrice,
                                changePercent = quote.formattedPercentChange,
                                trend = quote.trend.toPricelyTrend(),
                                isMuted = !state.isConnected,
                                onClick = { onSymbolClick(quote.symbol) },
                            )
                        }
                    }
                }

                when {
                    state is FeedUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.dimens.space16),
                            contentAlignment = Alignment.Center,
                        ) {
                            PricelyText(
                                text = stringResource(R.string.connection_status_loading),
                                style = AppTheme.typography.bodyMedium,
                                color = AppTheme.colors.textSecondary,
                            )
                        }
                    }

                    state is FeedUiState.Error && showErrorOverlay -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.dimens.space16),
                            contentAlignment = Alignment.Center,
                        ) {
                            PricelyStateView(
                                icon = painterResource(R.drawable.ic_sync),
                                title = stringResource(R.string.feed_error_title),
                                description = stringResource(R.string.feed_error_description),
                            )
                        }
                    }
                }
            } else {
                when (state) {
                    is FeedUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                color = AppTheme.colors.loadingIndicatorActive,
                                trackColor = AppTheme.colors.loadingIndicatorTrack,
                            )
                        }
                    }

                    is FeedUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            PricelyStateView(
                                icon = painterResource(R.drawable.ic_sync),
                                title = stringResource(R.string.feed_error_title),
                                description = stringResource(R.string.feed_error_description),
                            )
                        }
                    }

                    is FeedUiState.Content -> {}
                }
            }
        }
    }
}

private val previewQuoteModels = listOf(
    QuoteUiModel(
        symbol = "NFLX",
        companyName = "Netflix, Inc.",
        formattedPrice = "$188.91",
        formattedPercentChange = "+1.29%",
        trend = Trend.Up,
    ),
    QuoteUiModel(
        symbol = "AAPL",
        companyName = "Apple Inc.",
        formattedPrice = "$178.32",
        formattedPercentChange = "-0.87%",
        trend = Trend.Down,
    ),
    QuoteUiModel(
        symbol = "META",
        companyName = "Meta Platforms",
        formattedPrice = "$138.15",
        formattedPercentChange = "+1.29%",
        trend = Trend.Up,
    ),
)

private fun previewContentState(isConnected: Boolean) = FeedUiState.Content(
    quotes = previewQuoteModels,
    isConnected = isConnected,
    connectionLabel = "Connected",
)

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Loading_Light() {
    PricelyPreview(isDark = false) {
        SymbolScreenContent(
            state = FeedUiState.Loading,
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Loading_Dark() {
    PricelyPreview(isDark = true) {
        SymbolScreenContent(
            state = FeedUiState.Loading,
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Success_Connected_Light() {
    PricelyPreview(isDark = false) {
        SymbolScreenContent(
            state = previewContentState(isConnected = true),
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Success_Connected_Dark() {
    PricelyPreview(isDark = true) {
        SymbolScreenContent(
            state = previewContentState(isConnected = true),
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Success_Disconnected_Light() {
    PricelyPreview(isDark = false) {
        SymbolScreenContent(
            state = previewContentState(isConnected = false),
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Success_Disconnected_Dark() {
    PricelyPreview(isDark = true) {
        SymbolScreenContent(
            state = previewContentState(isConnected = false),
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Error_Light() {
    PricelyPreview(isDark = false) {
        SymbolScreenContent(
            state = FeedUiState.Error,
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun Symbol_Error_Dark() {
    PricelyPreview(isDark = true) {
        SymbolScreenContent(
            state = FeedUiState.Error,
            modifier = Modifier,
            onSymbolClick = {},
            onToggleFeedClicked = {},
        )
    }
}
