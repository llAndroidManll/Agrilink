package com.app.agrilink.presentation.base

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.app.agrilink.shared.compose.state.FullScreenError
import com.app.agrilink.shared.compose.state.FullScreenLoader
import com.app.agrilink.shared.util.Constants.LOG_TAG

/**
 * Основной метод для экранов Jetpack Compose, взаимодействующих с объектами BaseViewModel.
 *
 * Этот метод обрабатывает различные состояния, включая:
 * - **Ошибка загрузки**: состояние, когда возникает исключение при запросе или получении данных.
 * - **Загрузка данных**: состояние, когда получение данных находится в процессе.
 * - **Полученные данные**: состояние, когда данные успешно получены.
 *
 * @param error Состояние, указывающее на ошибку при запросе или получении данных.
 * @param loader Состояние, показывающее, что данные находятся в процессе загрузки.
 * @param data Состояние, когда данные успешно загружены.
 */
@Composable
fun <DataState : BaseDataState> BaseScreen(
    viewModel: BaseViewModel<DataState>,
    background: Color = Color.Unspecified,
    useStatusBarPadding: Boolean = true,
    onBack: (() -> Unit)? = null,
    toolbar: @Composable ((data: DataState?, onBack: () -> Unit) -> Unit)? = null,
    error: @Composable (BoxScope.(data: DataState?, retryAction: (() -> Unit)?) -> Unit)? = { _, retryAction -> FullScreenError { retryAction?.invoke() } },
    loader: @Composable (BoxScope.(isLoading: Boolean) -> Unit)? = { isLoading -> FullScreenLoader(isVisible = isLoading) },
    data: @Composable BoxScope.(data: DataState, isLoading: Boolean) -> Unit
) {
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> viewModel.onUIEvent(LifecycleUIEvent.OnCreate)
            Lifecycle.Event.ON_START -> viewModel.onUIEvent(LifecycleUIEvent.OnStart)
            Lifecycle.Event.ON_RESUME -> viewModel.onUIEvent(LifecycleUIEvent.OnResume)
            Lifecycle.Event.ON_PAUSE -> viewModel.onUIEvent(LifecycleUIEvent.OnPause)
            Lifecycle.Event.ON_STOP -> viewModel.onUIEvent(LifecycleUIEvent.OnStop)
            else -> Unit
        }
    }

    val state = remember(viewModel.baseState.value) { viewModel.baseState.value }

    Column(
        modifier = Modifier
            .background(color = background)
            .then(
                if (useStatusBarPadding) {
                    Modifier.statusBarsPadding()
                } else {
                    Modifier
                }
            )
    ) {
        toolbar?.invoke(state.getResultData()) {
            onBack?.invoke()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clipToBounds()

        ) {
            when (state) {
                is BaseScreenState.Error -> {
                    //В случай отсутствии Error view,
                    // мы будем вызывать Toast для отображение error-ов
                    if (error == null) {
                        Log.d(LOG_TAG, state.reason.message ?: "Empty Error")
                        Toast.makeText(LocalContext.current, state.reason.message, Toast.LENGTH_SHORT).show()
                    } else {
                        error(state.getResultData(), state.retryAction)
                    }
                }

                is BaseScreenState.Loading -> {
                    loader?.invoke(this, true)
                }

                else -> {
                    viewModel.baseState.value.getResultData()?.let { stateData ->
                        data(
                            stateData,
                            viewModel.baseState.value is BaseScreenState.Loading
                        )
                    }
                }
            }
        }
    }
}