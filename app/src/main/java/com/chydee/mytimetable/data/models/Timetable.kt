package com.chydee.mytimetable.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chydee.mytimetable.R
import com.chydee.mytimetable.utils.TIMETABLE_TABLE_NAME

@Entity(tableName = TIMETABLE_TABLE_NAME)
data class Timetable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "timetable_name") val tableName: String,
    @ColumnInfo(name = "timetable_label") val timetableLabel: Int = R.color.primaryDarkColor
)
