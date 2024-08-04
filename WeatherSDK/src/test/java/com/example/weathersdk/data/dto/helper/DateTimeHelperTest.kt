package com.example.weathersdk.data.dto.helper

import com.example.weathersdk.data.dto.helper.DateTimeHelper.HHMM_PATTERN
import com.example.weathersdk.data.dto.helper.DateTimeHelper.YYYYMMDD_T_HHMM_PATTERN
import io.kotest.matchers.shouldBe
import org.junit.Test


internal class DateTimeHelperTest {

    @Test
    fun `formatDateTime should return to expected output pattern and timezone`() {
        val result = DateTimeHelper.formatDateTime(
            inputPattern = YYYYMMDD_T_HHMM_PATTERN,
            outputPattern = HHMM_PATTERN,
            timeInString = "2024-08-04T02:00:00",
        )

        val expected = "02:00"

        result shouldBe expected
    }

    @Test
    fun `formatDateTimeToTimeZone should return to expected output pattern and timezone`() {
        val result = DateTimeHelper.formatDateTimeToTimeZone(
            timeStamp = 1722721975,
            inputTimeZone = "GMT",
            outputPattern = HHMM_PATTERN,
            outputTimezone = "Europe/Berlin"
        )

        val expected = "23:52"

        result shouldBe expected
    }
}