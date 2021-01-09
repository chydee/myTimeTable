package com.chydee.mytimetable.background

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class MyWorker @WorkerInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : Worker(ctx, params) {


    override fun doWork(): Result {
        return try {
            //Perform Task here
            //Return if successful
            Result.success()
        } catch (throwable: Throwable) {
            //Throw exception if anything goes wrong
            Timber.e(throwable)
            Result.failure()
        }
    }

}