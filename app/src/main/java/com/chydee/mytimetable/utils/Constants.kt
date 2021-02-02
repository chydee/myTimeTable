@file:JvmName("Constants")

package com.chydee.mytimetable.utils
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
