package com.chydee.mytimetable.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chydee.mytimetable.data.dao.LessonDao
import com.chydee.mytimetable.data.dao.TimetableDao
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.models.Timetable

@Database(entities = [Lesson::class, Timetable::class], version = 2, exportSchema = false)
abstract class MyTimetableDB : RoomDatabase() {
    abstract val lessonDao: LessonDao
    abstract val timetableDao: TimetableDao
}