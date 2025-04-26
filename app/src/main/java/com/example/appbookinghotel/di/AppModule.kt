package com.example.appbookinghotel.di

import com.example.appbookinghotel.model.ForgotPasswordRepository
import com.example.appbookinghotel.model.RegisterRepository
import com.example.appbookinghotel.model.SignInRepository
import com.example.appbookinghotel.utils.AppIntentNavigator
import com.example.core.intent.IntentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideForgotPassword() : ForgotPasswordRepository{
        return ForgotPasswordRepository()
    }

    @Provides
    @Singleton
    fun provideIntentActivity(): IntentActivity {
        return AppIntentNavigator()
    }

    @Provides
    @Singleton
    fun provideRegister() : RegisterRepository{
        return RegisterRepository()
    }

    @Provides
    @Singleton
    fun provideSignInRepository() : SignInRepository {
        return SignInRepository()
    }

}