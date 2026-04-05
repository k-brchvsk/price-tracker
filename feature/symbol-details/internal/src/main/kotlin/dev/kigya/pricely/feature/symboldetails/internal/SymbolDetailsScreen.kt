package dev.kigya.pricely.feature.symboldetails.internal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.core.designsystem.components.description.PricelyDescriptionCard
import dev.kigya.pricely.core.designsystem.components.icon.PricelySymbolIcon
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleIcon
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleStyle
import dev.kigya.pricely.core.designsystem.R as DesignSystemR
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.symboldetails.internal.model.SymbolDetailsUiState
import dev.kigya.pricely.util.compose.PricelyFlashingPriceText
import org.koin.androidx.compose.koinViewModel

@Composable
fun SymbolDetailsScreen(
    viewModel: SymbolDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SymbolDetailsScreenContent(
        state = state,
        onBackClick = viewModel::onBackClicked,
    )
}

@Composable
private fun SymbolDetailsScreenContent(
    state: SymbolDetailsUiState,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
    ) {
        DetailsTopBar(onBackClick = onBackClick)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.space16),
        ) {
            when {
                state.isUnknownSymbol -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
                    ) {
                        PricelyText(
                            text = stringResource(R.string.details_unknown_symbol),
                            style = AppTheme.typography.headingMedium,
                        )
                        PricelyText(
                            text = state.formattedPrice
                                ?: stringResource(R.string.details_price_unavailable),
                            style = AppTheme.typography.bodyLarge,
                            color = AppTheme.colors.textSecondary,
                        )
                    }
                }

                state.isLoading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
                    ) {
                        CircularProgressIndicator(
                            color = AppTheme.colors.loadingIndicatorActive,
                            trackColor = AppTheme.colors.loadingIndicatorTrack,
                            strokeWidth = AppTheme.dimens.loadingIndicatorStroke,
                        )
                        PricelyText(
                            text = stringResource(R.string.details_loading),
                            style = AppTheme.typography.bodyMedium,
                            color = AppTheme.colors.textSecondary,
                        )
                    }
                }

                else -> {
                    SymbolDetailsContent(state = state)
                }
            }
        }
    }
}

@Composable
private fun DetailsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = AppTheme.dimens.space16,
                vertical = AppTheme.dimens.space12,
            ),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(AppTheme.dimens.iconContainer)
                .border(
                    width = AppTheme.dimens.borderThin,
                    color = AppTheme.colors.descriptionCardBorder,
                    shape = LocalShapes.current.small,
                )
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.details_cd_back),
                tint = AppTheme.colors.textPrimary,
                modifier = Modifier.size(AppTheme.dimens.icon),
            )
        }
        PricelyText(
            text = stringResource(R.string.details_title),
            style = AppTheme.typography.headingMedium,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun SymbolDetailsContent(state: SymbolDetailsUiState) {
    val priceUnavailable = stringResource(R.string.details_price_unavailable)
    val percentPlaceholder = stringResource(DesignSystemR.string.design_system_placeholder_em_dash)
    Column(modifier = Modifier.fillMaxSize()) {
        val borderColor = when {
            !state.shouldShowTrendIndicators -> AppTheme.colors.cardBorder
            state.trend == Trend.Up -> AppTheme.colors.success
            state.trend == Trend.Down -> AppTheme.colors.error
            else -> AppTheme.colors.cardBorder
        }

        val cardTint = when {
            !state.shouldShowTrendIndicators -> AppTheme.colors.surface
            state.trend == Trend.Up -> AppTheme.colors.success.copy(alpha = 0.08f)
            state.trend == Trend.Down -> AppTheme.colors.error.copy(alpha = 0.08f)
            else -> AppTheme.colors.surface
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = AppTheme.shapes.medium,
            border = BorderStroke(AppTheme.dimens.borderThin, borderColor),
            colors = CardDefaults.cardColors(containerColor = cardTint),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.space16),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space12),
                ) {
                    PricelySymbolIcon(ticker = state.symbol)
                    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4)) {
                        PricelyText(
                            text = state.symbol,
                            style = AppTheme.typography.headingMedium,
                        )
                        state.companyName?.let { name ->
                            PricelyText(
                                text = name,
                                style = AppTheme.typography.bodySmall,
                                color = AppTheme.colors.textSecondary,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppTheme.dimens.space12))

                PricelyFlashingPriceText(
                    text = state.formattedPrice ?: priceUnavailable,
                    trend = state.trend.toPricelyTrend(),
                    emphasisColor = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.headingMedium,
                )

                if (state.shouldShowTrendIndicators) {
                    val changeColor = when (state.trend) {
                        Trend.Up -> AppTheme.colors.success
                        Trend.Down -> AppTheme.colors.error
                        Trend.Neutral -> AppTheme.colors.textSecondary
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
                        modifier = Modifier.padding(top = AppTheme.dimens.space4),
                    ) {
                        PricelyText(
                            text = state.formattedPercentChange ?: percentPlaceholder,
                            style = AppTheme.typography.bodySmall,
                            color = changeColor,
                        )
                        if (state.trend != Trend.Neutral) {
                            PricelyTrendCircleIcon(
                                trend = state.trend.toPricelyTrend(),
                                style = PricelyTrendCircleStyle.VERTICAL_ARROWS,
                                circleSize = AppTheme.dimens.space24,
                                iconSize = AppTheme.dimens.iconSmall,
                            )
                        }
                    }
                }
            }
        }

        state.description?.let { descriptionText ->
            Spacer(modifier = Modifier.height(AppTheme.dimens.space16))
            PricelyDescriptionCard(text = descriptionText)
        }
    }
}

private fun Trend.toPricelyTrend(): PricelyTrend = when (this) {
    Trend.Up -> PricelyTrend.UP
    Trend.Down -> PricelyTrend.DOWN
    Trend.Neutral -> PricelyTrend.NEUTRAL
}
