package dev.kigya.pricely.ui.mapper

import androidx.annotation.DrawableRes
import dev.kigya.pricely.R

@DrawableRes
fun String.symbolIconDrawableRes(): Int = when (this) {
    "AIRBNB" -> R.drawable.ic_airbnb
    "AMD" -> R.drawable.ic_amd
    "AAPL" -> R.drawable.ic_apple
    "DISCORD" -> R.drawable.ic_discord
    "DROPBOX" -> R.drawable.ic_dropbox
    "FB" -> R.drawable.ic_facebook
    "GITHUB" -> R.drawable.ic_github
    "GOOGL" -> R.drawable.ic_google
    "INTC" -> R.drawable.ic_intel
    "MA" -> R.drawable.ic_mastercard
    "META" -> R.drawable.ic_meta
    "NFLX" -> R.drawable.ic_netflix
    "NVDA" -> R.drawable.ic_nvidia
    "PAYPAL" -> R.drawable.ic_paypal
    "REDDIT" -> R.drawable.ic_reddit
    "SNAP" -> R.drawable.ic_snapchat
    "SPOT" -> R.drawable.ic_spotify
    "SQUARE" -> R.drawable.ic_square
    "STRIPE" -> R.drawable.ic_stripe
    "TSLA" -> R.drawable.ic_tesla
    "TIKTOK" -> R.drawable.ic_tiktok
    "UBER" -> R.drawable.ic_uber
    "VISA" -> R.drawable.ic_visa
    "WHATSAPP" -> R.drawable.ic_whatsapp
    "YOUTUBE" -> R.drawable.ic_youtube

    else -> R.drawable.ic_square
}