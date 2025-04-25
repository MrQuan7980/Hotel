package com.example.appbookinghotel.di

import com.example.appbookinghotel.utils.AppIntentNavigator
import com.example.core.intent.IntentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IntentModule {
    @Provides
    @Singleton
    fun provideIntentActivity(): IntentActivity {
        return AppIntentNavigator()
    }
}