package dev.kigya.pricely.feature.feed.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.core.designsystem.components.branding.PricelyAppMark
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionButton
import dev.kigya.pricely.core.designsystem.components.icon.PricelySymbolIcon
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionStatus
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleIcon
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleStyle
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleVariant
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.feed.internal.mapper.toPricelyActionState
import dev.kigya.pricely.feature.feed.internal.mapper.toPricelyConnectionState
import dev.kigya.pricely.feature.feed.internal.mapper.toPricelyTrend
import dev.kigya.pricely.feature.feed.internal.model.FeedUiState
import dev.kigya.pricely.feature.feed.internal.model.QuoteUiModel
import dev.kigya.pricely.util.compose.PricelyFlashingPriceText
import org.koin.androidx.compose.koinViewModel
import dev.kigya.pricely.core.designsystem.R as DesignSystemR

@Composable
fun FeedScreen(viewModel: FeedViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FeedScreenContent(
        state = state,
        onSymbolClick = viewModel::onSymbolClicked,
        onToggleFeedClick = viewModel::onToggleFeedClick,
    )
}

@Composable
private fun FeedScreenContent(
    state: FeedUiState,
    onSymbolClick: (String) -> Unit,
    onToggleFeedClick: () -> Unit,
) {
    val cachedQuotes = remember { mutableStateListOf<QuoteUiModel>() }
    var showErrorOverlay by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var shouldKeepListPinnedToTop by remember { mutableStateOf(true) }

    LaunchedEffect(state) {
        when (state) {
            is FeedUiState.Content -> {
                cachedQuotes.clear()
                cachedQuotes.addAll(state.quotes)
                showErrorOverlay = false
            }

            FeedUiState.Loading -> showErrorOverlay = false
            FeedUiState.Error -> showErrorOverlay = true
        }
    }

    val displayQuotes = if (state is FeedUiState.Content) state.quotes else cachedQuotes.toList()
    val quoteOrder = displayQuotes.map(QuoteUiModel::symbol)
    val shouldShowQuoteChangeAndTrend = state is FeedUiState.Content && state.isConnected

    LaunchedEffect(listState) {
        snapshotFlow {
            Triple(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset,
                listState.isScrollInProgress,
            )
        }.collect { (firstVisibleItemIndex, firstVisibleItemScrollOffset, isScrollInProgress) ->
            val isAtTop = firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0
            if (isScrollInProgress || isAtTop) {
                shouldKeepListPinnedToTop = isAtTop
            }
        }
    }

    LaunchedEffect(quoteOrder, shouldKeepListPinnedToTop) {
        if (
            shouldKeepListPinnedToTop &&
            displayQuotes.isNotEmpty() &&
            !listState.isAtAbsoluteTop()
        ) {
            listState.scrollToItem(index = 0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {
        FeedTopBar(
            state = state,
            onToggleFeedClick = onToggleFeedClick,
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (displayQuotes.isNotEmpty()) {
                Column {
                    PricelyText(
                        text = stringResource(R.string.feed_column_symbol),
                        style = AppTheme.typography.headingMedium,
                        modifier = Modifier.padding(
                            horizontal = AppTheme.dimens.space16,
                            vertical = AppTheme.dimens.space8,
                        ),
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState,
                        contentPadding = PaddingValues(bottom = AppTheme.dimens.space16),
                    ) {
                        items(
                            items = displayQuotes,
                            key = { it.symbol },
                        ) { quote ->
                            FeedQuoteRow(
                                quote = quote,
                                isMuted = !state.isConnected,
                                shouldShowChangeAndTrend = shouldShowQuoteChangeAndTrend,
                                onClick = { onSymbolClick(quote.symbol) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(
                                        fadeInSpec = null,
                                        fadeOutSpec = null,
                                        placementSpec = spring(
                                            dampingRatio = Spring.DampingRatioNoBouncy,
                                            stiffness = Spring.StiffnessLow,
                                        ),
                                    ),
                            )
                        }
                    }
                }

                when {
                    state is FeedUiState.Loading ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.dimens.space16),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(AppTheme.dimens.space48),
                                color = AppTheme.colors.loadingIndicatorActive,
                                trackColor = AppTheme.colors.loadingIndicatorTrack,
                                strokeWidth = AppTheme.dimens.loadingIndicatorStroke,
                            )
                        }

                    state is FeedUiState.Error && showErrorOverlay ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppTheme.dimens.space16),
                            contentAlignment = Alignment.Center,
                        ) {
                            FeedErrorState()
                        }
                }
            } else {
                when (state) {
                    FeedUiState.Loading ->
                        Column(modifier = Modifier.fillMaxSize()) {
                            PricelyText(
                                text = stringResource(R.string.feed_column_symbol),
                                style = AppTheme.typography.headingMedium,
                                modifier = Modifier.padding(
                                    horizontal = AppTheme.dimens.space16,
                                    vertical = AppTheme.dimens.space8,
                                ),
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(AppTheme.dimens.space48),
                                    color = AppTheme.colors.loadingIndicatorActive,
                                    trackColor = AppTheme.colors.loadingIndicatorTrack,
                                    strokeWidth = AppTheme.dimens.loadingIndicatorStroke,
                                )
                            }
                        }

                    FeedUiState.Error ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            FeedErrorState()
                        }

                    is FeedUiState.Content -> Unit
                }
            }
        }
    }
}

private fun LazyListState.isAtAbsoluteTop(): Boolean =
    firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0

@Composable
private fun FeedTopBar(
    state: FeedUiState,
    onToggleFeedClick: () -> Unit,
) {
    val toggleFeedContentDescription = stringResource(
        if (state is FeedUiState.Content) {
            if (state.isConnected) {
                R.string.feed_cd_pause_price_feed
            } else {
                R.string.feed_cd_start_price_feed
            }
        } else {
            R.string.feed_cd_start_price_feed
        },
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = AppTheme.dimens.space16,
                vertical = AppTheme.dimens.space12,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
        ) {
            PricelyAppMark(modifier = Modifier.size(AppTheme.dimens.iconContainer))
            PricelyText(
                text = stringResource(R.string.feed_brand_title),
                style = AppTheme.typography.headingMedium,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
        ) {
            PricelyConnectionStatus(
                labelResourceId = when (state) {
                    FeedUiState.Loading -> R.string.feed_connection_status_loading
                    FeedUiState.Error -> R.string.feed_connection_status_disconnected
                    is FeedUiState.Content ->
                        if (state.isConnected) {
                            R.string.feed_connection_status_connected
                        } else {
                            R.string.feed_connection_status_disconnected
                        }
                },
                state = state.toPricelyConnectionState(),
                labelColorOverride = if (state is FeedUiState.Error) {
                    AppTheme.colors.textSecondary
                } else {
                    null
                },
            )
            PricelyActionButton(
                state = state.toPricelyActionState(),
                icon = if (state is FeedUiState.Content) {
                    if (state.isConnected) {
                        Icons.Filled.Sync
                    } else {
                        Icons.Filled.SwapVert
                    }
                } else {
                    Icons.Filled.SwapVert
                },
                contentDescription = toggleFeedContentDescription,
                onClick = onToggleFeedClick,
            )
        }
    }
}

@Composable
private fun FeedQuoteRow(
    quote: QuoteUiModel,
    isMuted: Boolean,
    shouldShowChangeAndTrend: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val percentChangeLabel = quote.formattedPercentChange
        ?: stringResource(DesignSystemR.string.design_system_placeholder_em_dash)
    val trend = quote.trend.toPricelyTrend()
    val trendColor = when (trend) {
        PricelyTrend.UP -> AppTheme.colors.success
        PricelyTrend.DOWN -> AppTheme.colors.error
        PricelyTrend.NEUTRAL -> AppTheme.colors.textSecondary
    }
    val emphasis = if (isMuted) AppTheme.colors.textSecondary else AppTheme.colors.textPrimary
    val secondaryEmphasis = if (isMuted) {
        AppTheme.colors.textSecondary.copy(alpha = 0.85f)
    } else {
        AppTheme.colors.textSecondary
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isMuted) QUOTE_ROW_MUTED_ALPHA else 1f)
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimens.space16,
                vertical = AppTheme.dimens.space12,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
            modifier = Modifier.weight(1f),
        ) {
            PricelySymbolIcon(ticker = quote.symbol)
            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4)) {
                PricelyText(
                    text = quote.symbol,
                    style = AppTheme.typography.bodyEmphasized,
                    color = emphasis,
                )
                PricelyText(
                    text = quote.companyName,
                    style = AppTheme.typography.bodySmall,
                    color = secondaryEmphasis,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space12),
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
            ) {
                PricelyFlashingPriceText(
                    text = quote.formattedPrice,
                    trend = trend,
                    emphasisColor = emphasis,
                    style = AppTheme.typography.bodyEmphasized,
                )
                if (shouldShowChangeAndTrend) {
                    PricelyText(
                        text = percentChangeLabel,
                        style = AppTheme.typography.labelMedium,
                        color = trendColor,
                        modifier = Modifier.padding(top = AppTheme.dimens.space4),
                    )
                }
            }
            FeedQuoteTrendIcon(
                symbol = quote.symbol,
                domainTrend = quote.trend,
                pricelyTrend = trend,
                visible = shouldShowChangeAndTrend && quote.trend != Trend.Neutral,
            )
        }
    }
}

@Composable
private fun FeedQuoteTrendIcon(
    symbol: String,
    domainTrend: Trend,
    pricelyTrend: PricelyTrend,
    visible: Boolean,
) {
    Box(
        modifier = Modifier.size(AppTheme.dimens.space24),
        contentAlignment = Alignment.Center,
    ) {
        key(symbol, domainTrend, visible) {
            AnimatedVisibility(
                visible = visible,
                enter = when (domainTrend) {
                    Trend.Up ->
                        slideInHorizontally(
                            animationSpec = tween(TREND_SLIDE_IN_DURATION_MS, easing = FastOutSlowInEasing),
                            initialOffsetX = { full -> full },
                        ) +
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = TREND_FADE_IN_DURATION_MS,
                                    delayMillis = TREND_FADE_IN_DELAY_MS,
                                    easing = FastOutSlowInEasing,
                                ),
                            )
                    Trend.Down ->
                        slideInHorizontally(
                            animationSpec = tween(TREND_SLIDE_IN_DURATION_MS, easing = FastOutSlowInEasing),
                            initialOffsetX = { full -> -full },
                        ) +
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = TREND_FADE_IN_DURATION_MS,
                                    delayMillis = TREND_FADE_IN_DELAY_MS,
                                    easing = FastOutSlowInEasing,
                                ),
                            )
                    Trend.Neutral ->
                        fadeIn(animationSpec = tween(TREND_NEUTRAL_ENTER_DURATION_MS))
                },
                exit = when (domainTrend) {
                    Trend.Up ->
                        slideOutHorizontally(
                            animationSpec = tween(TREND_SLIDE_OUT_DURATION_MS, easing = FastOutLinearInEasing),
                            targetOffsetX = { full -> full },
                        ) +
                            fadeOut(
                                animationSpec = tween(TREND_FADE_OUT_DURATION_MS, easing = FastOutLinearInEasing),
                            )
                    Trend.Down ->
                        slideOutHorizontally(
                            animationSpec = tween(TREND_SLIDE_OUT_DURATION_MS, easing = FastOutLinearInEasing),
                            targetOffsetX = { full -> -full },
                        ) +
                            fadeOut(
                                animationSpec = tween(TREND_FADE_OUT_DURATION_MS, easing = FastOutLinearInEasing),
                            )
                    Trend.Neutral ->
                        fadeOut(animationSpec = tween(TREND_NEUTRAL_EXIT_DURATION_MS))
                },
            ) {
                PricelyTrendCircleIcon(
                    trend = pricelyTrend,
                    style = PricelyTrendCircleStyle.VERTICAL_ARROWS,
                    variant = PricelyTrendCircleVariant.SOFT_BORDERED,
                    circleSize = AppTheme.dimens.space24,
                    iconSize = AppTheme.dimens.iconSmall,
                )
            }
        }
    }
}

@Composable
private fun FeedErrorState() {
    Card(
        shape = AppTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimens.space24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space12),
        ) {
            Icon(
                imageVector = Icons.Filled.SwapVert,
                contentDescription = stringResource(R.string.feed_cd_error_state_icon),
                tint = AppTheme.colors.primary,
                modifier = Modifier.size(AppTheme.dimens.space48),
            )
            PricelyText(
                text = stringResource(R.string.feed_error_title),
                style = AppTheme.typography.headingMedium,
                textAlign = TextAlign.Center,
            )
            PricelyText(
                text = stringResource(R.string.feed_error_description),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.textErrorDescription,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private const val QUOTE_ROW_MUTED_ALPHA = 0.72f
private const val TREND_SLIDE_IN_DURATION_MS = 360
private const val TREND_FADE_IN_DURATION_MS = 280
private const val TREND_FADE_IN_DELAY_MS = 48
private const val TREND_SLIDE_OUT_DURATION_MS = 220
private const val TREND_FADE_OUT_DURATION_MS = 180
private const val TREND_NEUTRAL_ENTER_DURATION_MS = 1
private const val TREND_NEUTRAL_EXIT_DURATION_MS = 160
