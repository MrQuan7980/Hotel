package com.example.appbookinghotel.di

import com.example.appbookinghotel.model.ForgotPasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForgotModule {
    @Provides
    @Singleton
    fun provideForgotPassword() : ForgotPasswordRepository{
        return ForgotPasswordRepository()
    }
}