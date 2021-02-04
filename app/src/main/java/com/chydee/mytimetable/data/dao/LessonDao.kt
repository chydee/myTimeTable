package com.chydee.mytimetable.data.dao

import androidx.room.*
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.utils.LESSON_TABLE_NAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Defines the method for using the Lesson class with room
 */
@Dao
interface LessonDao {

    /**
     * This method inserts the lesson into the period_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lesson: Lesson)

    /**
     * This method inserts a list of periods into the period_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lesson: List<Lesson>)

    /**
     * This method updates a lesson in the period_table
     */
    @Update
    fun update(lesson: Lesson)

    /**
     * This method deletes a lesson from the period_table
     */
    @Delete
    fun delete(lesson: Lesson)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM $LESSON_TABLE_NAME")
    fun deleteAll()


    /**
     * Get all periods from period_table
     *
     */
    @Query("SELECT * FROM $LESSON_TABLE_NAME ORDER BY id DESC")
    fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM $LESSON_TABLE_NAME WHERE day_of_week LIKE :today")
    fun getTodayLesson(today: String): Flow<List<Lesson>>

    @Query("SELECT * FROM $LESSON_TABLE_NAME WHERE day_of_week LIKE :today")
    fun getTodayLessons(today: String): List<Lesson>

    @ExperimentalCoroutinesApi
    fun getTodayLessonDistinctUntilChanged(today: String) =
        getTodayLesson(today).distinctUntilChanged()

}