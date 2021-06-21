package ca.uqac.programmationmobile.messages.utils

import java.time.LocalDate
import java.util.*

class Time {
    fun getDate(timestamp: String) : String {
        val date = timestamp.split(" ")[0]

        val year = date.split("-")[0]
        val month = date.split("-")[1]
        val day = date.split("-")[2]

        val monthString = when (month) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jul"
            "08" -> "Aug"
            "09" -> "Sep"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"

            else -> ""
        }
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        if (year == currentYear) return "$monthString $day"
        else return "$monthString $day $year"
    }

    fun getTime(timestamp: String) : String {
        val time = timestamp.split(" ")[1]

        val hours = time.split(":")[0]
        val minutes = time.split(":")[1]

        return "$hours:$minutes"
    }
}