package com.example.feature_admin.di

import com.example.feature_admin.model.room.AddRoomRepository
import com.example.feature_admin.model.room.ListRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminModule {
    @Provides
    @Singleton
    fun provideListRoom() : ListRoomRepository {
        return ListRoomRepository()
    }

    @Provides
    @Singleton
    fun provideAddRoom() : AddRoomRepository {
        return AddRoomRepository()
    }
}