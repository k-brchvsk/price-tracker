package dev.kigya.pricely.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import dev.kigya.pricely.feature.feed.api.usecase.StartPriceSessionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainActivityViewModel(
    private val startPriceSession: StartPriceSessionUseCase,
) : ViewModel() {

    private val _deepLinkIntents = MutableSharedFlow<Intent>(extraBufferCapacity = 1)
    val deepLinkIntents: SharedFlow<Intent> = _deepLinkIntents.asSharedFlow()

    fun onActivityCreate() {
        startPriceSession()
    }

    fun onNewIntent(intent: Intent) {
        _deepLinkIntents.tryEmit(intent)
    }
}
