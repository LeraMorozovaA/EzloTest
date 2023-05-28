package com.example.ezlotest.di

import android.content.Context
import com.example.ezlotest.api.ApiService
import com.example.ezlotest.data.dao.DeviceDao
import com.example.ezlotest.data.local.LocalStorageService
import com.example.ezlotest.api.DeviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        apiService: ApiService,
        dao: DeviceDao,
        localStorageService: LocalStorageService
    ): DeviceRepositoryImpl = DeviceRepositoryImpl(context, apiService, dao, localStorageService)
}