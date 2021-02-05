package com.chydee.mytimetable.data.dataAccessArchitecture

import com.chydee.mytimetable.data.dao.LessonDao
import com.chydee.mytimetable.data.dao.TimetableDao
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.models.Timetable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBHelperImpl @Inject constructor(
    private val lessonDao: LessonDao,
    private val timetableDao: TimetableDao
) :
    DBHelper {
    override suspend fun insert(lesson: Lesson) {
        lessonDao.insert(lesson)
    }

    override suspend fun insert(lesson: List<Lesson>) {
        lessonDao.insert(lesson)
    }

    override suspend fun insert(timetable: Timetable) {
        timetableDao.insert(timetable)
    }

    override suspend fun update(lesson: Lesson) {
        lessonDao.update(lesson)
    }

    override suspend fun update(timetable: Timetable) {
        timetableDao.update(timetable)
    }

    override suspend fun delete(lesson: Lesson) {
        lessonDao.delete(lesson)
    }

    override suspend fun delete(timetable: Timetable) {
        timetableDao.delete(timetable)
    }

    override suspend fun deleteAll() {
        lessonDao.deleteAll()
    }

    override suspend fun deleteAllTimetableContents(tableName: String) {
        lessonDao.deleteTimetableContents(tableName)
    }

    override suspend fun getAllLessons(tableName: String): Flow<List<Lesson>> {
        return lessonDao.getAllLessons(tableName)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getTodayLesson(today: String, tableName: String): Flow<List<Lesson>> {
        return lessonDao.getTodayLessonDistinctUntilChanged(today, tableName)
    }

    override suspend fun deleteAllTimetable() {
        timetableDao.deleteAll()
    }

    override suspend fun getAllTimetable(): Flow<List<Timetable>> {
        return timetableDao.getAllTimetables()
    }

}