@file:JvmName("Constants")

package com.chydee.mytimetable.utils

import com.chydee.mytimetable.data.models.Color
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

const val PERIOD_TABLE_NAME = "period"


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
