package com.chydee.mytimetable.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chydee.mytimetable.data.dao.PeriodDao
import com.chydee.mytimetable.data.models.Period

@Database(entities = [Period::class], version = 0, exportSchema = false)
abstract class MyTimetableDB : RoomDatabase() {
    abstract val periodDao: PeriodDao
}