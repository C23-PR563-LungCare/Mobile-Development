package com.bangkit.lungcare.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatData(currentDate: String?, targetTimeZone: String): String {
        val instant = Instant.parse(currentDate)
        val formatter =
            DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(
                ZoneId.of(targetTimeZone)
            )
        return formatter.format(instant)
    }
}