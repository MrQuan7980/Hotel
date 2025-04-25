package com.example.appbookinghotel.di

import com.example.appbookinghotel.model.SignInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Provides
    @Singleton
    fun provideSignInRepository() : SignInRepository {
        return SignInRepository()
    }
}