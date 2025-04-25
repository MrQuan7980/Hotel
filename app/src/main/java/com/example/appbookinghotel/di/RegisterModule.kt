package com.example.appbookinghotel.di

import com.example.appbookinghotel.model.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegister() : RegisterRepository{
        return RegisterRepository()
    }
}