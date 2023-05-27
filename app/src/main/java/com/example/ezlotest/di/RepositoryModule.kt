package com.example.ezlotest.di

import com.example.ezlotest.api.ApiService
import com.example.ezlotest.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): DeviceRepository = DeviceRepository(apiService)
}