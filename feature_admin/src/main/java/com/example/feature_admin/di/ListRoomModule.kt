package com.example.feature_admin.di

import com.example.feature_admin.model.ListRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListRoomModule {
    @Provides
    @Singleton
    fun provideListRoom() : ListRoomRepository{
        return ListRoomRepository()
    }
}