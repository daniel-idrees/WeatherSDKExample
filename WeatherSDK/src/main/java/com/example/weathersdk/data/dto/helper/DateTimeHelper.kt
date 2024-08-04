package com.example.weathersdk.data.dto.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Internal use only.
 *
 * Helper object for date/time formatting utilities.
 *
 */

internal object DateTimeHelper {
    const val HHMM_PATTERN = "HH:mm"
    const val YYYYMMDD_T_HHMM_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * Formats a date/time string from an input pattern and timezone to an output pattern and timezone.
     *
     * @param inputPattern The pattern of the input date/time string.
     * @param outputPattern The pattern of the output date/time string.
     * @param timeInString The date/time string to be formatted.
     * @param inputTimeZone The timezone of the input date/time string, default is null.
     * @param outputTimezone The timezone of the output date/time string, default is null.
     * @return The formatted date/time string.
     * @throws ParseException If the input date/time string cannot be parsed.
     */
    fun formatDateTime(
        inputPattern: String,
        outputPattern: String,
        timeInString: String,
        inputTimeZone: String? = null,
        outputTimezone: String? = null
    ): String {
        val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH).apply {
            inputTimeZone?.let {
                timeZone = TimeZone.getTimeZone(inputTimeZone)
            }
        }

        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault()).apply {
            outputTimezone?.let {
                timeZone = TimeZone.getTimeZone(outputTimezone)
            }
        }
        val date = checkNotNull(inputFormat.parse(timeInString))
        return outputFormat.format(date)
    }

    /**
     * Formats a timestampstring from a timezone to an output pattern and timezone.
     *
     * @param timeStamp The timestamp string to be formatted.
     * @param inputTimeZone The timezone of the input date/time string.
     * @param outputPattern The pattern of the output date/time string.
     * @param outputTimezone The timezone of the output date/time string.
     * @return The formatted date/time string.
     */
    fun formatDateTimeToTimeZone(
        timeStamp: Long,
        inputTimeZone: String,
        outputPattern: String,
        outputTimezone: String
    ): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timeStamp * 1000L
            timeZone = TimeZone.getTimeZone(inputTimeZone)
        }

        val dateFormat = SimpleDateFormat(outputPattern, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(outputTimezone)
        }

        return dateFormat.format(calendar.time)
    }
}