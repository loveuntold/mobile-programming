package com.example.news_app.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun toRelativeTime(publishedAt: String): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val publishedDate = format.parse(publishedAt) ?: return ""
        val diffMillis = System.currentTimeMillis() - publishedDate.time
        val diffMinutes = diffMillis / 60_000
        val diffHours = diffMinutes / 60
        val diffDays = diffHours / 24

        when {
            diffDays > 0 -> "$diffDays hari lalu"
            diffHours > 0 -> "$diffHours jam lalu"
            diffMinutes > 0 -> "$diffMinutes menit lalu"
            else -> "Baru saja"
        }
    } catch (e: Exception) {
        ""
    }
}
