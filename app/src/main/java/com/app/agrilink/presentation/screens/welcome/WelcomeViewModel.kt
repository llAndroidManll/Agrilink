package com.app.agrilink.presentation.screens.welcome

import androidx.navigation.NavHostController
import com.app.agrilink.domain.data.LoginState.*
import com.app.agrilink.domain.useCases.CheckLoginStateUseCase
import com.app.agrilink.navigation.NavigationScreens
import com.app.agrilink.presentation.base.BaseEvent
import com.app.agrilink.presentation.base.BaseViewModel
import com.app.agrilink.presentation.base.LifecycleUIEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = WelcomeViewModel.Factory::class)
class WelcomeViewModel @AssistedInject constructor(
    @Assisted private val navHostController: NavHostController,
    private val checkLoginStateUseCase: CheckLoginStateUseCase
) : BaseViewModel<WelcomeState>() {

    @AssistedFactory
    interface Factory {
        fun create(navHostController: NavHostController): WelcomeViewModel
    }

    override fun createDefaultDataState(): WelcomeState {
        return WelcomeState()
    }

    override fun reduce(event: BaseEvent) {
        if (event == LifecycleUIEvent.OnCreate) {
            createRequest {
                checkLoginStateUseCase()
            }.launch {
                when (it) {
                    LOGGED -> navHostController.navigate(NavigationScreens.HomeScreen.route)
                    NOT_LOGGED -> navHostController.navigate(NavigationScreens.AuthScreen.route)
                }
            }
        }
    }
}