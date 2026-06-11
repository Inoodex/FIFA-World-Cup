package com.worldcup2026.app.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatMatchDate(utcString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(utcString) ?: return utcString
            val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            try {
                val inputFormat2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                inputFormat2.timeZone = TimeZone.getTimeZone("UTC")
                val date = inputFormat2.parse(utcString) ?: return utcString
                val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
                outputFormat.format(date)
            } catch (e2: Exception) {
                utcString
            }
        }
    }

    fun formatMatchTime(utcString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(utcString) ?: return "--:--"
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault()
            outputFormat.format(date) + " (local)"
        } catch (e: Exception) {
            "--:--"
        }
    }

    fun formatRoundName(round: String): String {
        return when (round.lowercase().replace(" ", "_")) {
            "group" -> "Group Stage"
            "round_of_32" -> "Round of 32"
            "round_of_16" -> "Round of 16"
            "quarter_final", "quarterfinal" -> "Quarter Final"
            "semi_final", "semifinal" -> "Semi Final"
            "third_place" -> "3rd Place"
            "final" -> "Final"
            else -> round.replace("_", " ").replaceFirstChar { it.uppercase() }
        }
    }
}
