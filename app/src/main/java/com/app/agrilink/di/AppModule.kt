package com.app.agrilink.di

import com.app.agrilink.data.auth.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*@Provides
    @Singleton
    fun provideGoogleAuthUiClient(): GoogleAuthUiClient {
        return GoogleAuthUiClient(

        )
    }*/
}