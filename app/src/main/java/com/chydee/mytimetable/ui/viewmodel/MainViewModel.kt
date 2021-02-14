@file:Suppress("DEPRECATION")

package com.chydee.mytimetable.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chydee.mytimetable.data.dataAccessArchitecture.DBHelperImpl
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.models.Timetable
import com.chydee.mytimetable.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(private val dbImpl: DBHelperImpl) : ViewModel() {

    private var viewModelJob = SupervisorJob()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _currentDayLessons = MutableLiveData<List<Lesson>>()
    val currentDayLessons: LiveData<List<Lesson>>
        get() = _currentDayLessons

    private val _timetable = MutableLiveData<Timetable>()
    val timetable: LiveData<Timetable>
        get() = _timetable

    private fun setTimetable(timetable: Timetable) {
        _timetable.postValue(timetable)
    }


    private val _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson>
        get() = _lesson

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>>
        get() = _lessons


    fun addLesson(lesson: Lesson) {
        scope.launch {
            try {
                withContext(Dispatchers.Default) {
                    dbImpl.insert(lesson)
                    _lesson.postValue(lesson)
                }
            } catch (ex: Exception) {
                _lesson.postValue(null)
                Timber.d(ex)
            }
        }
    }

    fun addLessons(lessons: List<Lesson>) {
        scope.launch {
            Status.LOADING
            try {
                withContext(Dispatchers.Default) {
                    dbImpl.insert(lessons)
                    _lessons.postValue(lessons)
                }
            } catch (ex: Exception) {
                _lessons.postValue(emptyList())
                Timber.d(ex)
            }
        }
    }

    fun saveTimetableInfo(timetable: Timetable) {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.insert(timetable)
                setTimetable(timetable)
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                ex.printStackTrace()
            }
        }
    }

    fun updateTimetableInfo(timetable: Timetable) {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.update(timetable)
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
            }
        }
    }

    fun deleteTimetable(timetable: Timetable) {
        scope.launch {
            Status.LOADING
            try {
                deleteAllTimetableContent(timetable.tableName) //Delete timetable contents first
                dbImpl.delete(timetable) //Then delete the timetable itself
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
            }
        }
    }

    private fun deleteAllTimetableContent(tableName: String) {
        scope.launch {
            try {
                dbImpl.deleteAllTimetableContents(tableName)
                Status.SUCCESS
            } catch (e: Exception) {
                Status.ERROR
                Timber.d(e.localizedMessage)
            }
        }
    }

    fun updateLesson(lesson: Lesson) {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.update(lesson)
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
            }
        }
    }

    fun removeLesson(lesson: Lesson) {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.delete(lesson)
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
            }
        }
    }

    fun clearAllLessons() {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.deleteAll()
                Status.SUCCESS
            } catch (e: Exception) {
                Status.ERROR
                Timber.d(e)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getLessonsForCurrentDay(currentDay: String, timetableName: String) {
        scope.launch {
            Status.LOADING
            dbImpl.getTodayLesson(currentDay, timetableName).catch { ex ->
                Status.ERROR
                Timber.d(ex.localizedMessage)
                _currentDayLessons.postValue(null)
            }
                    .collect { lessons ->
                        _currentDayLessons.postValue(lessons)
                        Status.SUCCESS
                    }
        }
    }

    private val _timetables = MutableLiveData<List<Timetable>>()
    val timetables: LiveData<List<Timetable>>
        get() = _timetables

    /**
     * Gets all the available timetables from the database
     */
    fun getAllTimetables() {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.getAllTimetable().catch {
                    Status.ERROR
                    Timber.d(it.localizedMessage)
                }.collect {
                    _timetables.postValue(it)
                    Status.SUCCESS
                }
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}