package com.chydee.mytimetable.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chydee.mytimetable.utils.Constants

@Entity(tableName = Constants.PERIOD_TABLE_NAME)
data class Period(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "course_code") val courseCode: String,
    @ColumnInfo(name = "course_title") val courseTitle: String,
    @ColumnInfo(name = "course_label") val courseLabel: Int,
    @ColumnInfo(name = "course_tutor") val courseTutor: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
)
