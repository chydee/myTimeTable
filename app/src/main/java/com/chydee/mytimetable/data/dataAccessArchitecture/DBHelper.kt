package com.chydee.mytimetable.data.dataAccessArchitecture

import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.models.Timetable
import kotlinx.coroutines.flow.Flow

interface DBHelper {
    suspend fun insert(lesson: Lesson)

    suspend fun insert(lesson: List<Lesson>)

    suspend fun update(lesson: Lesson)

    suspend fun delete(lesson: Lesson)

    suspend fun deleteAll()

    suspend fun deleteAllTimetableContents(tableName: String)

    suspend fun getAllLessons(tableName: String): Flow<List<Lesson>>

    suspend fun getTodayLesson(today: String, tableName: String): Flow<List<Lesson>>

    suspend fun insert(timetable: Timetable)

    suspend fun update(timetable: Timetable)

    suspend fun delete(timetable: Timetable)

    suspend fun deleteAllTimetable()

    suspend fun getAllTimetable(): Flow<List<Timetable>>

}