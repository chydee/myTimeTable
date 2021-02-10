package com.chydee.mytimetable.di

import com.chydee.mytimetable.data.preference.AppPrefsStorage
import com.chydee.mytimetable.data.preference.PreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {
    @Binds
    abstract fun providesPreferenceStorage(
        appPreferenceStorage: AppPrefsStorage
    ): PreferenceStorage
}