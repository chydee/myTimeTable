package com.chydee.mytimetable.background

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    @ExperimentalCoroutinesApi
    fun refreshPeriodicWork(context: Context) {

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 07:30:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 7)
        dueDate.set(Calendar.MINUTE, 30)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)

        Log.d("MyWorker", "time difference $minutes")

        //define constraints
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(false)
            .build()


        val refreshCpnWork = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(minutes, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("myWorkManager")
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshCpnWork
        )

    }
}