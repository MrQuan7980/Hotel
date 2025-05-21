package com.example.appbookinghotel.di

import com.example.appbookinghotel.model.auth.ForgotPasswordRepository
import com.example.appbookinghotel.model.auth.RegisterRepository
import com.example.appbookinghotel.model.auth.SignInRepository
import com.example.appbookinghotel.model.room.IntroduceRepository
import com.example.appbookinghotel.model.room.ReviewRoomRepository
import com.example.appbookinghotel.model.room.UserRoomRepository
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
    fun provideForgotPassword() : ForgotPasswordRepository {
        return ForgotPasswordRepository()
    }

    @Provides
    @Singleton
    fun provideIntentActivity(): IntentActivity {
        return AppIntentNavigator()
    }

    @Provides
    @Singleton
    fun provideRegister() : RegisterRepository {
        return RegisterRepository()
    }

    @Provides
    @Singleton
    fun provideSignInRepository() : SignInRepository {
        return SignInRepository()
    }

    @Provides
    @Singleton
    fun provideUserRoomRepository() : UserRoomRepository {
        return UserRoomRepository()
    }

    @Provides
    @Singleton
    fun provideReviewRoomRepository() : ReviewRoomRepository{
        return ReviewRoomRepository()
    }

    @Provides
    @Singleton
    fun provideIntroduce() : IntroduceRepository{
        return IntroduceRepository()
    }
}