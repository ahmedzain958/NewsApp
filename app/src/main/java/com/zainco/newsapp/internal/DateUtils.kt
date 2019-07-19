package com.zainco.newsapp.internal

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateUtils {
    fun getDateFormatted(unFormattedDate: String): String {
        val parsed = ZonedDateTime.parse(unFormattedDate)
        // convert to another timezone
        val z = parsed.withZoneSameInstant(ZoneId.of("America/Los_Angeles"))
        // format output
        val fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.ENGLISH)
        return fmt.format(z)
    }

}