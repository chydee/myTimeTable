package com.chydee.mytimetable.data.dao

import androidx.room.*
import com.chydee.mytimetable.data.models.Timetable
import com.chydee.mytimetable.utils.TIMETABLE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

/**
 * Defines the method for using the Timetable class with room
 */
@Dao
interface TimetableDao {

    /**
     * This method inserts the lesson into the period_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timetable: Timetable)

    /**
     * This method updates a Timetable in the timetable table
     */
    @Update
    fun update(timetable: Timetable)

    /**
     * This method deletes a timetable from the timetable table
     */
    @Delete
    fun delete(timetable: Timetable)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM $TIMETABLE_TABLE_NAME")
    fun deleteAll()


    /**
     * Get all timetable from timetable table
     *
     */
    @Query("SELECT * FROM $TIMETABLE_TABLE_NAME ORDER BY id DESC")
    fun getAllTimetables(): Flow<List<Timetable>>
}