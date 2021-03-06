@file:JvmName("Constants")

package com.chydee.mytimetable.utils

import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Color
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Color as Colors


// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
@JvmField
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"

const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1


const val DB_NAME = "myTimeTable_DB"
const val TEST_DB_NAME = "test_db_name"

const val LESSON_TABLE_NAME = "period"
const val TIMETABLE_TABLE_NAME = "timetable"


const val CURRENT_DAY_FORMAT = "EEEE"


val colors = arrayListOf(
        Color("Blue", Colors.BLUE),
        Color("Yellow", Colors.YELLOW),
        Color("Green", Colors.GREEN),
        Color("Red", Colors.RED),
        Color("Magenta", Colors.MAGENTA),
        Color("Black", Colors.BLACK),
        Color("Dark-Gray", Colors.DKGRAY),
        Color("Cyan", Colors.CYAN),
        Color("Gray", Colors.GRAY),
        Color("Light-Gray", Colors.LTGRAY)
)


fun getDayOfTheWeek(): String {
    return SimpleDateFormat(CURRENT_DAY_FORMAT, Locale.getDefault()).format(Date())
}

fun getSubjectAvatar(subject: String): Int {
    return if (subject.contains("CSC") || subject.contains("Computer")) R.drawable.ic__85_microprocessor
    else if (subject.contains("Programming") || subject.contains("Code")) R.drawable.ic__86_science
    else if (subject.contains("Database") || subject.contains("SQL")) R.drawable.ic__44_database
    else if (subject.contains("MTH") || subject.contains("Mathematics")) R.drawable.ic__01_maths
    else if (subject.contains("Art") || subject.contains("Creative")) R.drawable.ic__10_paintbrush
    else if (subject.contains("Music") || subject.contains("MUS")) R.drawable.ic__26_music
    else if (subject.contains("Economics")) R.drawable.ic__13_bar_chart
    else if (subject.contains("Biology")) R.drawable.ic__18_dna
    else if (subject.contains("Science")) R.drawable.ic__38_science_and_tech
    else if (subject.contains("PHY") || subject.contains("Physics")) R.drawable.ic__11_science_research
    else R.drawable.ic__05_archive

}
