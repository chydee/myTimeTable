package com.chydee.mytimetable.di

import android.content.Context
import androidx.room.Room
import com.chydee.mytimetable.data.MyTimetableDB
import com.chydee.mytimetable.data.dao.LessonDao
import com.chydee.mytimetable.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MyTimetableDB::class.java,
        Constants.TEST_DB_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePeriodDao(database: MyTimetableDB): LessonDao = database.lessonDao
}