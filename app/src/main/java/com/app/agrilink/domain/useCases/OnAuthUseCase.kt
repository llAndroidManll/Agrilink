package com.app.agrilink.domain.useCases

import android.content.Intent
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.domain.preferences.IDataStoreManagement

class OnAuthUseCase(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataStoreManagement: IDataStoreManagement
) {
    suspend operator fun invoke(intent: Intent): UserData {
        val result = googleAuthUiClient.signInWithIntent(intent)
        dataStoreManagement.saveData(result, IDataStoreManagement.USER_DATA_KEY)
        return result
    }
}