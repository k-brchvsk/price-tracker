package dev.kigya.pricely.domain.catalog

import dev.kigya.pricely.domain.model.SymbolInfo

object SymbolCatalog {

    val entries: List<SymbolInfo> = listOf(
        SymbolInfo(
            ticker = "AAPL",
            displayName = "Apple Inc.",
            description = "Consumer electronics, software, and services ecosystem including iPhone, Mac, and iCloud.",
        ),
        SymbolInfo(
            ticker = "GOOGL",
            displayName = "Alphabet Inc.",
            description = "Search, advertising, and cloud services powering the Google ecosystem.",
        ),
        SymbolInfo(
            ticker = "META",
            displayName = "Meta Platforms Inc.",
            description = "Social platforms including Facebook, Instagram, and virtual reality products.",
        ),
        SymbolInfo(
            ticker = "NFLX",
            displayName = "Netflix Inc.",
            description = "Streaming entertainment platform with original content and global distribution.",
        ),
        SymbolInfo(
            ticker = "NVDA",
            displayName = "NVIDIA Corporation",
            description = "Leader in GPUs and AI computing for data centers, gaming, and machine learning.",
        ),
        SymbolInfo(
            ticker = "AMD",
            displayName = "Advanced Micro Devices",
            description = "High-performance CPUs and GPUs for gaming, PCs, and data centers.",
        ),
        SymbolInfo(
            ticker = "INTC",
            displayName = "Intel Corporation",
            description = "Semiconductor company producing processors and computing platforms.",
        ),
        SymbolInfo(
            ticker = "TIKTOK",
            displayName = "TikTok",
            description = "Short-form video platform for entertainment and content creation.",
        ),
        SymbolInfo(
            ticker = "UBER",
            displayName = "Uber Technologies Inc.",
            description = "Ride-sharing, food delivery, and mobility platform services.",
        ),
        SymbolInfo(
            ticker = "SPOT",
            displayName = "Spotify Technology",
            description = "Music streaming platform offering podcasts and digital audio services.",
        ),
        SymbolInfo(
            ticker = "SNAP",
            displayName = "Snap Inc.",
            description = "Social media and messaging platform focused on visual communication.",
        ),
        SymbolInfo(
            ticker = "DISCORD",
            displayName = "Discord",
            description = "Communication platform for communities, gaming, and collaboration.",
        ),
        SymbolInfo(
            ticker = "DROPBOX",
            displayName = "Dropbox Inc.",
            description = "Cloud storage and file synchronization services.",
        ),
        SymbolInfo(
            ticker = "FB",
            displayName = "Facebook",
            description = "Social networking platform for connecting users and businesses.",
        ),
        SymbolInfo(
            ticker = "GITHUB",
            displayName = "GitHub",
            description = "Platform for version control and collaborative software development.",
        ),
        SymbolInfo(
            ticker = "PAYPAL",
            displayName = "PayPal Holdings Inc.",
            description = "Digital payments platform for online and mobile transactions.",
        ),
        SymbolInfo(
            ticker = "STRIPE",
            displayName = "Stripe",
            description = "Payment infrastructure for online businesses and financial services.",
        ),
        SymbolInfo(
            ticker = "SQUARE",
            displayName = "Block Inc.",
            description = "Financial services and mobile payments including Square and Cash App.",
        ),
        SymbolInfo(
            ticker = "MA",
            displayName = "Mastercard Inc.",
            description = "Global payment network enabling electronic transactions.",
        ),
        SymbolInfo(
            ticker = "VISA",
            displayName = "Visa Inc.",
            description = "Worldwide payment technology connecting consumers and businesses.",
        ),
        SymbolInfo(
            ticker = "TSLA",
            displayName = "Tesla Inc.",
            description = "Electric vehicles, renewable energy, and autonomous driving technology.",
        ),
        SymbolInfo(
            ticker = "YOUTUBE",
            displayName = "YouTube",
            description = "Video sharing platform with user-generated and premium content.",
        ),
        SymbolInfo(
            ticker = "WHATSAPP",
            displayName = "WhatsApp",
            description = "Messaging platform for secure communication worldwide.",
        ),
        SymbolInfo(
            ticker = "REDDIT",
            displayName = "Reddit Inc.",
            description = "Community-driven discussion platform with user-generated content.",
        ),
        SymbolInfo(
            ticker = "AIRBNB",
            displayName = "Airbnb Inc.",
            description = "Marketplace for short-term rentals and travel experiences.",
        ),
    )

    init {
        require(entries.size == 25)
    }

    private val byTicker: Map<String, SymbolInfo> = entries.associateBy { it.ticker }

    fun metaOrNull(ticker: String): SymbolInfo? = byTicker[ticker]

    fun isKnownTicker(ticker: String): Boolean = ticker in byTicker
}
