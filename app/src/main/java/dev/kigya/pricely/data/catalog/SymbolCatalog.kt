package dev.kigya.pricely.data.catalog

object SymbolCatalog {

    val entries: List<SymbolMeta> = listOf(
        SymbolMeta(
            ticker = "AAPL",
            displayName = "Apple Inc.",
            description = "Consumer electronics and software ecosystem; iPhone and services drive recurring revenue.",
        ),
        SymbolMeta(
            ticker = "MSFT",
            displayName = "Microsoft Corporation",
            description = "Cloud, productivity, and enterprise software including Azure and Office.",
        ),
        SymbolMeta(
            ticker = "GOOGL",
            displayName = "Alphabet Inc. Class A",
            description = "Search, advertising, and cloud; parent of Google services and Android ecosystem.",
        ),
        SymbolMeta(
            ticker = "AMZN",
            displayName = "Amazon.com Inc.",
            description = "E-commerce, logistics, and AWS cloud infrastructure.",
        ),
        SymbolMeta(
            ticker = "META",
            displayName = "Meta Platforms Inc.",
            description = "Social products including Facebook, Instagram, and Reality Labs.",
        ),
        SymbolMeta(
            ticker = "NVDA",
            displayName = "NVIDIA Corporation",
            description = "Accelerated computing, GPUs, and AI data center platforms.",
        ),
        SymbolMeta(
            ticker = "TSLA",
            displayName = "Tesla Inc.",
            description = "Electric vehicles, energy storage, and autonomy software.",
        ),
        SymbolMeta(
            ticker = "BTC",
            displayName = "Bitcoin",
            description = "Decentralized digital asset used as a store of value and settlement layer.",
        ),
        SymbolMeta(
            ticker = "ETH",
            displayName = "Ethereum",
            description = "Programmable blockchain supporting smart contracts and decentralized applications.",
        ),
        SymbolMeta(
            ticker = "SOL",
            displayName = "Solana",
            description = "High-throughput blockchain focused on performance and developer tooling.",
        ),
        SymbolMeta(
            ticker = "JPM",
            displayName = "JPMorgan Chase & Co.",
            description = "Global investment bank and consumer banking franchise.",
        ),
        SymbolMeta(
            ticker = "V",
            displayName = "Visa Inc.",
            description = "Payment network connecting consumers, merchants, and financial institutions.",
        ),
        SymbolMeta(
            ticker = "JNJ",
            displayName = "Johnson & Johnson",
            description = "Healthcare conglomerate spanning pharmaceuticals, medtech, and consumer health.",
        ),
        SymbolMeta(
            ticker = "WMT",
            displayName = "Walmart Inc.",
            description = "Retail and grocery at scale with growing e-commerce and marketplace services.",
        ),
        SymbolMeta(
            ticker = "PG",
            displayName = "Procter & Gamble Co.",
            description = "Household brands across fabric care, beauty, grooming, and health.",
        ),
        SymbolMeta(
            ticker = "MA",
            displayName = "Mastercard Inc.",
            description = "Global payments technology company enabling card and digital transactions.",
        ),
        SymbolMeta(
            ticker = "DIS",
            displayName = "The Walt Disney Company",
            description = "Media, parks, experiences, and streaming platforms.",
        ),
        SymbolMeta(
            ticker = "NFLX",
            displayName = "Netflix Inc.",
            description = "Subscription streaming entertainment and original content production.",
        ),
        SymbolMeta(
            ticker = "AMD",
            displayName = "Advanced Micro Devices Inc.",
            description = "CPUs, GPUs, and adaptive computing for PCs, data centers, and embedded.",
        ),
        SymbolMeta(
            ticker = "INTC",
            displayName = "Intel Corporation",
            description = "Semiconductor manufacturing and platforms for client and data center compute.",
        ),
        SymbolMeta(
            ticker = "CSCO",
            displayName = "Cisco Systems Inc.",
            description = "Networking, security, collaboration, and observability for enterprises.",
        ),
        SymbolMeta(
            ticker = "XOM",
            displayName = "Exxon Mobil Corporation",
            description = "Integrated oil and gas exploration, production, and chemicals.",
        ),
        SymbolMeta(
            ticker = "CVX",
            displayName = "Chevron Corporation",
            description = "Upstream and downstream energy with LNG and lower-carbon projects.",
        ),
        SymbolMeta(
            ticker = "BAC",
            displayName = "Bank of America Corp.",
            description = "Consumer banking, wealth management, and global markets.",
        ),
        SymbolMeta(
            ticker = "KO",
            displayName = "The Coca-Cola Company",
            description = "Beverage brands and global distribution with a focus on sparkling and still drinks.",
        ),
    )

    init {
        require(entries.size == 25)
    }

    private val byTicker: Map<String, SymbolMeta> = entries.associateBy { it.ticker }

    fun metaOrNull(ticker: String): SymbolMeta? = byTicker[ticker]

    fun isKnownTicker(ticker: String): Boolean = ticker in byTicker
}
