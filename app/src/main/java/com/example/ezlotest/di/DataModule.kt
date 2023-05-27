package com.example.ezlotest.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.ezlotest.data.AppDatabase
import com.example.ezlotest.data.dao.DeviceDao
import com.example.ezlotest.data.local.LocalStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "database-name"
    ).build()

    @Singleton
    @Provides
    fun provideGiphyDao(appDatabase: AppDatabase): DeviceDao = appDatabase.deviceDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("local_storage")
            }
        )

    @Singleton
    @Provides
    fun provideLocalStorageService(prefs: DataStore<Preferences>): LocalStorageService =
        LocalStorageService(prefs)


}