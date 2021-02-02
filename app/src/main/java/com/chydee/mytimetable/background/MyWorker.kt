package com.chydee.mytimetable.background

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chydee.mytimetable.data.dataAccessArchitecture.DBHelperImpl
import com.chydee.mytimetable.utils.CURRENT_DAY_FORMAT
import com.chydee.mytimetable.utils.notify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class MyWorker @WorkerInject constructor(
    private val dbImpl: DBHelperImpl,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {


    override suspend fun doWork(): Result {

        val appContext = applicationContext

        return try {
            showNotificationSomeMinutesToClass(appContext)
            notifyMeOfClassesForTheDay(appContext)
            //Return if successful
            Result.success()
        } catch (throwable: Throwable) {
            //Throw exception if anything goes wrong
            Timber.e(throwable)
            Result.failure()
        }
    }

    private fun showNotificationSomeMinutesToClass(context: Context) {
        notify("Upcoming class", "You have a class in 30mins time\n Click to view details", context)
    }

    private suspend fun notifyMeOfClassesForTheDay(context: Context) {
        withContext(Dispatchers.Default) {
            dbImpl.getTodayLesson(
                SimpleDateFormat(
                    CURRENT_DAY_FORMAT,
                    Locale.getDefault()
                ).format(Date())
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