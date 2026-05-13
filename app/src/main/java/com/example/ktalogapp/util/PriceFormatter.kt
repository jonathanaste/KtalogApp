package com.example.ktalogapp.util

import java.text.NumberFormat
import java.util.Locale

fun Double.formatPrice(): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}
