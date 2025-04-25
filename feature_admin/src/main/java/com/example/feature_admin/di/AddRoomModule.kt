package com.example.feature_admin.di

import com.example.feature_admin.model.AddRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddRoomModule {
    @Provides
    @Singleton
    fun provideAddRoom() : AddRoomRepository {
        return AddRoomRepository()
    }
}