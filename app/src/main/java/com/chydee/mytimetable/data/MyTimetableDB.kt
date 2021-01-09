package com.chydee.mytimetable.data

import androidx.room.Database
import androidx.room.RoomDatabase

//TODO: @Database annotation must  specify list of entities
@Database(entities = [/*Patient::class*/], version = 0, exportSchema = false)
abstract class MyTimetableDB : RoomDatabase()