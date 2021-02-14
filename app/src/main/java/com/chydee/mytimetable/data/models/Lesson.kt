package com.chydee.mytimetable.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chydee.mytimetable.R
import com.chydee.mytimetable.utils.LESSON_TABLE_NAME

@Entity(tableName = LESSON_TABLE_NAME)
data class Lesson(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @ColumnInfo(name = "timetable_id") val tableId: Long,
        @ColumnInfo(name = "timetable_name") val tableName: String,
        @ColumnInfo(name = "course_code") val courseCode: String,
        @ColumnInfo(name = "course_title") val courseTitle: String,
        @ColumnInfo(name = "course_description") val courseDescription: String,
        @ColumnInfo(name = "course_tutor") val courseTutor: String,
        @ColumnInfo(name = "place") val place: String,
        @ColumnInfo(name = "day_of_week") val dayOfWeek: String,
        @ColumnInfo(name = "start_time") val startTime: String,
        @ColumnInfo(name = "end_time") val endTime: String,
        @ColumnInfo(name = "lesson_illustration") val illusID: Int = R.drawable.ic__05_archive
)
