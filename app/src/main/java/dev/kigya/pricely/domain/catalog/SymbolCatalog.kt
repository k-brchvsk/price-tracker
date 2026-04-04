package dev.kigya.pricely.domain.catalog

import dev.kigya.pricely.domain.model.SymbolInfo

object SymbolCatalog {

    val entries: List<SymbolInfo> = listOf(
        SymbolInfo(
            ticker = "AAPL",
            displayName = "Apple Inc.",
            description = "Consumer electronics and software ecosystem; iPhone and services drive recurring revenue.",
        ),
        SymbolInfo(
            ticker = "MSFT",
            displayName = "Microsoft Corporation",
            description = "Cloud, productivity, and enterprise software including Azure and Office.",
        ),
        SymbolInfo(
            ticker = "GOOGL",
            displayName = "Alphabet Inc. Class A",
            description = "Search, advertising, and cloud; parent of Google services and Android ecosystem.",
        ),
        SymbolInfo(
            ticker = "AMZN",
            displayName = "Amazon.com Inc.",
            description = "E-commerce, logistics, and AWS cloud infrastructure.",
        ),
        SymbolInfo(
            ticker = "META",
            displayName = "Meta Platforms Inc.",
            description = "Social products including Facebook, Instagram, and Reality Labs.",
        ),
        SymbolInfo(
            ticker = "NVDA",
            displayName = "NVIDIA Corporation",
            description = "Accelerated computing, GPUs, and AI data center platforms.",
        ),
        SymbolInfo(
            ticker = "TSLA",
            displayName = "Tesla Inc.",
            description = "Electric vehicles, energy storage, and autonomy software.",
        ),
        SymbolInfo(
            ticker = "BTC",
            displayName = "Bitcoin",
            description = "Decentralized digital asset used as a store of value and settlement layer.",
        ),
        SymbolInfo(
            ticker = "ETH",
            displayName = "Ethereum",
            description = "Programmable blockchain supporting smart contracts and decentralized applications.",
        ),
        SymbolInfo(
            ticker = "SOL",
            displayName = "Solana",
            description = "High-throughput blockchain focused on performance and developer tooling.",
        ),
        SymbolInfo(
            ticker = "JPM",
            displayName = "JPMorgan Chase & Co.",
            description = "Global investment bank and consumer banking franchise.",
        ),
        SymbolInfo(
            ticker = "V",
            displayName = "Visa Inc.",
            description = "Payment network connecting consumers, merchants, and financial institutions.",
        ),
        SymbolInfo(
            ticker = "JNJ",
            displayName = "Johnson & Johnson",
            description = "Healthcare conglomerate spanning pharmaceuticals, medtech, and consumer health.",
        ),
        SymbolInfo(
            ticker = "WMT",
            displayName = "Walmart Inc.",
            description = "Retail and grocery at scale with growing e-commerce and marketplace services.",
        ),
        SymbolInfo(
            ticker = "PG",
            displayName = "Procter & Gamble Co.",
            description = "Household brands across fabric care, beauty, grooming, and health.",
        ),
        SymbolInfo(
            ticker = "MA",
            displayName = "Mastercard Inc.",
            description = "Global payments technology company enabling card and digital transactions.",
        ),
        SymbolInfo(
            ticker = "DIS",
            displayName = "The Walt Disney Company",
            description = "Media, parks, experiences, and streaming platforms.",
        ),
        SymbolInfo(
            ticker = "NFLX",
            displayName = "Netflix Inc.",
            description = "Subscription streaming entertainment and original content production.",
        ),
        SymbolInfo(
            ticker = "AMD",
            displayName = "Advanced Micro Devices Inc.",
            description = "CPUs, GPUs, and adaptive computing for PCs, data centers, and embedded.",
        ),
        SymbolInfo(
            ticker = "INTC",
            displayName = "Intel Corporation",
            description = "Semiconductor manufacturing and platforms for client and data center compute.",
        ),
        SymbolInfo(
            ticker = "CSCO",
            displayName = "Cisco Systems Inc.",
            description = "Networking, security, collaboration, and observability for enterprises.",
        ),
        SymbolInfo(
            ticker = "XOM",
            displayName = "Exxon Mobil Corporation",
            description = "Integrated oil and gas exploration, production, and chemicals.",
        ),
        SymbolInfo(
            ticker = "CVX",
            displayName = "Chevron Corporation",
            description = "Upstream and downstream energy with LNG and lower-carbon projects.",
        ),
        SymbolInfo(
            ticker = "BAC",
            displayName = "Bank of America Corp.",
            description = "Consumer banking, wealth management, and global markets.",
        ),
        SymbolInfo(
            ticker = "KO",
            displayName = "The Coca-Cola Company",
            description = "Beverage brands and global distribution with a focus on sparkling and still drinks.",
        ),
    )

    init {
        require(entries.size == 25)
    }

    private val byTicker: Map<String, SymbolInfo> = entries.associateBy { it.ticker }

    fun metaOrNull(ticker: String): SymbolInfo? = byTicker[ticker]

    fun isKnownTicker(ticker: String): Boolean = ticker in byTicker
}
