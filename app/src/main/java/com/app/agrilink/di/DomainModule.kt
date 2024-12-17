package com.app.agrilink.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.data.source.local.DataStoreManagementImpl
import com.app.agrilink.domain.preferences.IDataStoreManagement
import com.app.agrilink.domain.useCases.CheckLoginStateUseCase
import com.app.agrilink.domain.useCases.OnAuthUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideDataStoreManagement(dataStore: DataStore<Preferences>): IDataStoreManagement {
        //Optimize gson object if need
        val gson = Gson()
        return DataStoreManagementImpl(dataStore, gson)
    }

    @Provides
    fun provideCheckLoginStateUseCase(dataIDataStoreManagement: IDataStoreManagement): CheckLoginStateUseCase {
        return CheckLoginStateUseCase(dataIDataStoreManagement)
    }

    @Provides
    fun provideOnAuthUseCase(
        dataIDataStoreManagement: IDataStoreManagement,
        googleAuthUiClient: GoogleAuthUiClient
    ): OnAuthUseCase {
        return OnAuthUseCase(dataStoreManagement = dataIDataStoreManagement, googleAuthUiClient = googleAuthUiClient)
    }
}