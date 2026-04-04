package dev.kigya.pricely.ui.symboldetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.description.PricelyDescriptionCard
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIconVariant
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.components.topbar.PricelyTopBar
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.ui.mapper.symbolIconDrawableRes
import dev.kigya.pricely.ui.mapper.toPricelyTrend
import dev.kigya.pricely.util.compose.PricelyFlashingPriceText
import org.koin.androidx.compose.koinViewModel

@Composable
fun SymbolDetailsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SymbolDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.background,
        topBar = {
            PricelyTopBar(
                startContent = {
                    PricelyIcon(
                        painter = painterResource(R.drawable.ic_chevron_back),
                        contentDescription = stringResource(R.string.topbar_back),
                        tint = AppTheme.colors.backIcon,
                        modifier = Modifier.clickable(onClick = onBackClick),
                    )
                },
                centerContent = {
                    PricelyText(
                        text = stringResource(R.string.topbar_symbol_details),
                        style = AppTheme.typography.headingMedium,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppTheme.dimens.space16),
        ) {
            when {
                state.unknownSymbol -> {
                    PricelyText(
                        text = stringResource(R.string.details_unknown_symbol),
                        style = AppTheme.typography.headingMedium,
                        color = AppTheme.colors.textPrimary,
                    )
                    PricelyText(
                        text = state.formattedPrice,
                        style = AppTheme.typography.bodyLarge,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.padding(top = AppTheme.dimens.space8),
                    )
                }

                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
                        ) {
                            CircularProgressIndicator(
                                color = AppTheme.colors.loadingIndicatorActive,
                                trackColor = AppTheme.colors.loadingIndicatorTrack,
                            )
                            PricelyText(
                                text = stringResource(R.string.details_loading),
                                style = AppTheme.typography.bodyMedium,
                                color = AppTheme.colors.textSecondary,
                            )
                        }
                    }
                }

                else -> {
                    val borderColor = when {
                        !state.showTrendIndicators -> AppTheme.colors.cardBorder
                        state.trend == Trend.Up -> AppTheme.colors.success
                        state.trend == Trend.Down -> AppTheme.colors.error
                        else -> AppTheme.colors.cardBorder
                    }

                    val cardTint = when {
                        !state.showTrendIndicators -> AppTheme.colors.surface
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
                                PricelyIcon(
                                    painter = painterResource(state.symbol.symbolIconDrawableRes()),
                                    contentDescription = stringResource(R.string.cd_stock_icon),
                                    variant = PricelyIconVariant.CIRCLE_BACKGROUND,
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
                                ) {
                                    PricelyText(
                                        text = state.symbol,
                                        style = AppTheme.typography.headingMedium,
                                        color = AppTheme.colors.textPrimary,
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

                            Spacer(modifier = Modifier.padding(top = AppTheme.dimens.space12))

                            PricelyFlashingPriceText(
                                text = state.formattedPrice,
                                trend = state.trend.toPricelyTrend(),
                                emphasisColor = AppTheme.colors.textPrimary,
                                style = AppTheme.typography.headingMedium,
                            )

                            val changeColor = when {
                                !state.showTrendIndicators -> AppTheme.colors.textSecondary
                                state.trend == Trend.Up -> AppTheme.colors.success
                                state.trend == Trend.Down -> AppTheme.colors.error
                                else -> AppTheme.colors.textSecondary
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
                                modifier = Modifier.padding(top = AppTheme.dimens.space4),
                            ) {
                                PricelyText(
                                    text = state.formattedPercentChange,
                                    style = AppTheme.typography.bodySmall,
                                    color = changeColor,
                                )

                                if (state.showTrendIndicators) {
                                    when (state.trend) {
                                        Trend.Up -> {
                                            PricelyIcon(
                                                painter = painterResource(R.drawable.ic_arrow_up),
                                                contentDescription = stringResource(R.string.cd_trend_up),
                                                variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                                                tint = AppTheme.colors.success,
                                            )
                                        }

                                        Trend.Down -> {
                                            PricelyIcon(
                                                painter = painterResource(R.drawable.ic_arrow_down),
                                                contentDescription = stringResource(R.string.cd_trend_down),
                                                variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                                                tint = AppTheme.colors.error,
                                            )
                                        }

                                        Trend.Neutral -> {}
                                    }
                                }
                            }
                        }
                    }

                    state.description?.let { desc ->
                        Spacer(modifier = Modifier.padding(top = AppTheme.dimens.space16))
                        PricelyDescriptionCard(text = desc)
                    }
                }
            }
        }
    }
}
