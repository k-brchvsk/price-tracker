package dev.kigya.pricely.domain.repository

import dev.kigya.pricely.domain.model.PriceSessionState
import kotlinx.coroutines.flow.StateFlow

interface PriceSessionStateSource {
    val state: StateFlow<PriceSessionState>
}
