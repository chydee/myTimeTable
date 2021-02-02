package com.chydee.mytimetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chydee.mytimetable.data.dataAccessArchitecture.DBHelperImpl
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dbImpl: DBHelperImpl) : ViewModel() {

    private var viewModelJob = Job()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _currentDayLessons = MutableLiveData<List<Lesson>>()
    val currentDayLessons: LiveData<List<Lesson>>
        get() = _currentDayLessons


    fun addLesson(lesson: Lesson) {
        Status.LOADING
        scope.launch {
            dbImpl.insert(lesson)
            Status.SUCCESS
        }
    }

    fun addLessons(lessons: List<Lesson>) {
        scope.launch {
            Status.LOADING
            try {
                dbImpl.insert(lessons)
                Status.SUCCESS
            } catch (ex: Exception) {
                Status.ERROR
                Timber.d(ex)
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
    fun getLessonsForCurrentDay(currentDay: String) {
        scope.launch {
            Status.LOADING
            dbImpl.getTodayLesson(currentDay).catch { ex ->
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


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}