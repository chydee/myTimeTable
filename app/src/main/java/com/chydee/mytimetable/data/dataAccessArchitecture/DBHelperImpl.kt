package com.chydee.mytimetable.data.dataAccessArchitecture

import com.chydee.mytimetable.data.dao.LessonDao
import com.chydee.mytimetable.data.models.Lesson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBHelperImpl @Inject constructor(private val lessonDao: LessonDao) :
    DBHelper {
    override suspend fun insert(lesson: Lesson) {
        lessonDao.insert(lesson)
    }

    override suspend fun insert(lesson: List<Lesson>) {
        lessonDao.insert(lesson)
    }

    override suspend fun update(lesson: Lesson) {
        lessonDao.update(lesson)
    }

    override suspend fun delete(lesson: Lesson) {
        lessonDao.delete(lesson)
    }

    override suspend fun deleteAll() {
        lessonDao.deleteAll()
    }

    override suspend fun getAllLessons(): Flow<List<Lesson>> {
        return lessonDao.getAllLessons()
    }

    @ExperimentalCoroutinesApi
    override suspend fun getTodayLesson(today: String): Flow<List<Lesson>> {
        return lessonDao.getTodayLessonDistinctUntilChanged(today)
    }

}