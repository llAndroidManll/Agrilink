package com.app.agrilink.domain.useCases

import com.app.agrilink.domain.data.LoginState
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.domain.preferences.IDataStoreManagement
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last

class CheckLoginStateUseCase(private val dataStore: IDataStoreManagement) {
    suspend operator fun invoke(): LoginState {
        val userData = dataStore.getData(UserData::class.java, IDataStoreManagement.USER_DATA_KEY).firstOrNull()
        return when {
            userData == null || userData.userId.isEmpty() -> {
                LoginState.NOT_LOGGED
            }
            else -> {
                LoginState.LOGGED
            }
        }
    }
}