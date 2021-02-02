package com.chydee.mytimetable.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chydee.mytimetable.data.dao.LessonDao
import com.chydee.mytimetable.data.models.Lesson

@Database(entities = [Lesson::class], version = 0, exportSchema = false)
abstract class MyTimetableDB : RoomDatabase() {
    abstract val lessonDao: LessonDao
}