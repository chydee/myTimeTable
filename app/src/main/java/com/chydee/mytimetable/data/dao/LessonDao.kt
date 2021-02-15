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


    @Query("DELETE FROM $LESSON_TABLE_NAME WHERE timetable_name LIKE :tableName")
    fun deleteTimetableContents(tableName: String)


    /**
     * Get all periods from period_table
     *
     */
    @Query("SELECT * FROM $LESSON_TABLE_NAME WHERE timetable_name LIKE :tableName ORDER BY day_of_week ASC")
    fun getAllLessons(tableName: String): Flow<List<Lesson>>

    /**
     *  Get current day's lessons where Timetable name is equal to...
     *  ...@param tableName and ...
     *  ... Current day of the week is equal to...
     *  ... @param today
     */
    @Query("SELECT * FROM $LESSON_TABLE_NAME WHERE timetable_name LIKE :tableName AND  day_of_week LIKE :today ORDER BY day_of_week ASC")
    fun getTodayLessons(today: String, tableName: String): Flow<List<Lesson>>

    @Query("SELECT * FROM $LESSON_TABLE_NAME WHERE timetable_name LIKE :tableName AND day_of_week LIKE :today ORDER BY day_of_week ASC")
    fun getTodayClasses(today: String, tableName: String): List<Lesson>

    @ExperimentalCoroutinesApi
    fun getTodayLessonDistinctUntilChanged(today: String, tableName: String) =
            getTodayLessons(today, tableName).distinctUntilChanged()

}