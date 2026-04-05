package dev.kigya.pricely.core.designsystem.symbol

internal object SymbolBrandIconUrls {

    private const val SIMPLE_ICONS_CDN_BASE = "https://cdn.simpleicons.org"

    private val simpleIconSlugByTicker: Map<String, String> = mapOf(
        "AAPL" to "apple",
        "GOOGL" to "google",
        "META" to "meta",
        "NFLX" to "netflix",
        "NVDA" to "nvidia",
        "AMD" to "amd",
        "INTC" to "intel",
        "TIKTOK" to "tiktok",
        "UBER" to "uber",
        "SPOT" to "spotify",
        "SNAP" to "snapchat",
        "DISCORD" to "discord",
        "DROPBOX" to "dropbox",
        "FB" to "facebook",
        "GITHUB" to "github",
        "PAYPAL" to "paypal",
        "STRIPE" to "stripe",
        "SQUARE" to "square",
        "MA" to "mastercard",
        "VISA" to "visa",
        "TSLA" to "tesla",
        "YOUTUBE" to "youtube",
        "WHATSAPP" to "whatsapp",
        "REDDIT" to "reddit",
        "AIRBNB" to "airbnb",
    )

    fun svgUrlForTicker(ticker: String): String? =
        simpleIconSlugByTicker[ticker]?.let { slug -> "$SIMPLE_ICONS_CDN_BASE/$slug" }
}
