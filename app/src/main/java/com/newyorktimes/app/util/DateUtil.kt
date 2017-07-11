package com.newyorktimes.app.util


import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class DateUtil {

    companion object {

        fun formatDate(originalDate: String): String {

            try {
                val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                val timeZone = TimeZone.getTimeZone("UTC")
                sdf.timeZone = timeZone
                val date = sdf.parse(originalDate)
                val calendar = Calendar.getInstance()
                calendar.time = date
                @SuppressLint("WrongConstant")
                val month = calendar.getDisplayName(
                        Calendar.MONTH,
                        Calendar.SHORT,
                        Locale.getDefault())
                val dayOFMonth = calendar.get(Calendar.DAY_OF_MONTH)
                val year = calendar.get(Calendar.YEAR)
                return StringBuilder()
                        .append(month)
                        .append(" ")
                        .append(dayOFMonth.toString())
                        .append(", ")
                        .append(year)
                        .toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return originalDate
        }
    }

}
