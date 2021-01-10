package com.chydee.mytimetable.data.dao

import androidx.room.*
import com.chydee.mytimetable.data.models.Period
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Defines the method for using the Period class with room
 */
@Dao
interface PeriodDao {

    /**
     * This method inserts the period into the period_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(period: Period)

    /**
     * This method inserts a list of periods into the period_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(period: List<Period>)

    /**
     * This method updates a period in the period_table
     */
    @Update
    fun update(period: Period)

    /**
     * This method deletes a period from the period_table
     */
    @Delete
    fun delete(period: Period)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM period_table")
    fun deleteAll()


    /**
     * Get all periods from period_table
     *
     */
    @Query("SELECT * FROM period_table ORDER BY id DESC")
    fun getAllPeriods(): Flow<List<Period>>

    @Query("SELECT * FROM period_table WHERE day_of_week LIKE :today")
    fun getTodayPeriod(today: String): Flow<List<Period>>

    @ExperimentalCoroutinesApi
    fun getTodayPeriodDistinctUntilChanged(today: String) =
        getTodayPeriod(today).distinctUntilChanged()

}