package com.chydee.mytimetable.data.preference

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {
    // check for current day
    val defaultTimetableName: Flow<String>
    suspend fun setDefaultTimetableName(name: String)

    //Set allowed Notification
    val isNotificationEnabled: Flow<Boolean>
    suspend fun setIsNotificationEnabled(isEnabled: Boolean)

    /***
     * clears all the stored data
     */
    suspend fun clearPreferenceStorage()
}