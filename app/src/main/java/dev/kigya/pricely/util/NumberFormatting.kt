package dev.kigya.pricely.util

import java.util.Locale

fun formatPrice(value: Double): String =
    String.format(Locale.US, "%.2f", value)

fun formatPercent(pct: Double?): String =
    pct?.let { String.format(Locale.US, "%+.2f%%", it) } ?: "—"
