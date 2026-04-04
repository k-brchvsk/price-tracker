package dev.kigya.pricely.ui.mapper

import androidx.annotation.DrawableRes
import dev.kigya.pricely.R

@DrawableRes
fun String.symbolIconDrawableRes(): Int = when (this) {
    "AAPL" -> R.drawable.ic_apple
    "MSFT" -> R.drawable.ic_github
    "GOOGL" -> R.drawable.ic_google
    "AMZN" -> R.drawable.ic_stripe
    "META" -> R.drawable.ic_meta
    "NVDA" -> R.drawable.ic_nvidia
    "TSLA" -> R.drawable.ic_tesla
    "BTC" -> R.drawable.ic_square
    "ETH" -> R.drawable.ic_dropbox
    "SOL" -> R.drawable.ic_spotify
    "JPM" -> R.drawable.ic_paypal
    "V" -> R.drawable.ic_visa
    "JNJ" -> R.drawable.ic_facebook
    "WMT" -> R.drawable.ic_uber
    "PG" -> R.drawable.ic_whatsapp
    "MA" -> R.drawable.ic_mastercard
    "DIS" -> R.drawable.ic_youtube
    "NFLX" -> R.drawable.ic_netflix
    "AMD" -> R.drawable.ic_amd
    "INTC" -> R.drawable.ic_intel
    "CSCO" -> R.drawable.ic_discord
    "XOM" -> R.drawable.ic_snapchat
    "CVX" -> R.drawable.ic_tiktok
    "BAC" -> R.drawable.ic_reddit
    "KO" -> R.drawable.ic_airbnb
    else -> R.drawable.ic_square
}
