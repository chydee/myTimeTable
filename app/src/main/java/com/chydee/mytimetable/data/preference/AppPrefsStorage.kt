package com.chydee.mytimetable.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPrefsStorage @Inject constructor(@ApplicationContext context: Context) :
    PreferenceStorage {

    // since @Singleton scope is used, dataStore will have the same instance every time
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "AppPrefStorage")

    private object PreferencesKeys {
        val DEFAULT_TIMETABLE_NAME = stringPreferencesKey("pref_default_table_name")
        val IS_NOTIFICATION_ENABLED = booleanPreferencesKey("pref_notification_enabled")
    }

    override val defaultTimetableName: Flow<String>
        get() = dataStore.getValueAsFlow(PreferencesKeys.DEFAULT_TIMETABLE_NAME, "Table name")

    override suspend fun setDefaultTimetableName(name: String) {
        dataStore.setValue(PreferencesKeys.DEFAULT_TIMETABLE_NAME, name)
    }

    override val isNotificationEnabled: Flow<Boolean>
        get() = dataStore.getValueAsFlow(PreferencesKeys.IS_NOTIFICATION_ENABLED, true)

    override suspend fun setIsNotificationEnabled(isEnabled: Boolean) {
        dataStore.setValue(PreferencesKeys.IS_NOTIFICATION_ENABLED, isEnabled)
    }

    override suspend fun clearPreferenceStorage() {
        dataStore.edit {
            it.clear()
        }
    }


    /***
     * handy function to save key-value pairs in Preference. Sets or updates the value in Preference
     * @param key used to identify the preference
     * @param value the value to be saved in the preference
     */
    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        this.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    /***
     * handy function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [Flow] of [T]
     */
    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }
}