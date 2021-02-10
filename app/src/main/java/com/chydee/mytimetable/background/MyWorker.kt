package com.chydee.mytimetable.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chydee.mytimetable.data.dataAccessArchitecture.DBHelperImpl
import com.chydee.mytimetable.data.preference.PreferenceStorage
import com.chydee.mytimetable.utils.getDayOfTheWeek
import com.chydee.mytimetable.utils.notify
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltWorker
class MyWorker @AssistedInject constructor(
    private val dbImpl: DBHelperImpl,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var prefStorage: PreferenceStorage


    override suspend fun doWork(): Result {

        val appContext = applicationContext

        return try {
            try {
                showNotificationSomeMinutesToClass(appContext)
                notifyMeOfClassesForTheDay(appContext)
                //Return if successful
                Result.success()
            } catch (throwable: Throwable) {
                //Throw exception if anything goes wrong
                Timber.e(throwable)
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotificationSomeMinutesToClass(context: Context) {
        notify(
            "Upcoming class",
            "You have a class in 30 mins time\n Click to view details",
            context
        )
    }

    private suspend fun notifyMeOfClassesForTheDay(context: Context) {
        val timeTableName: String? = prefStorage.defaultTimetableName.firstOrNull()
        timeTableName?.let {
            Timber.d("Timetable name: $it")
        }
        withContext(Dispatchers.Default) {
            if (timeTableName != null) {
                dbImpl.getTodayLesson(
                    getDayOfTheWeek(), tableName = timeTableName
                ).catch {
                    it.localizedMessage
                }.collect { lessons ->
                    if (lessons.isNotEmpty()) {
                        val message = """
                            Hi, you have $lessons
                        """.trimIndent()
                        notify("Classes for today", message, context)
                    }
                }
            }
        }
    }
}