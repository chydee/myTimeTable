package com.chydee.mytimetable.data.dataAccessArchitecture

import com.chydee.mytimetable.data.models.Lesson
import kotlinx.coroutines.flow.Flow

interface DBHelper {
    suspend fun insert(lesson: Lesson)

    suspend fun insert(lesson: List<Lesson>)

    suspend fun update(lesson: Lesson)

    suspend fun delete(lesson: Lesson)

    suspend fun deleteAll()

    suspend fun getAllLessons(): Flow<List<Lesson>>

    suspend fun getTodayLesson(today: String): Flow<List<Lesson>>

}