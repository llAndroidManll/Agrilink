package com.app.agrilink.presentation.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.app.agrilink.shared.util.launchWithCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BaseViewModel<DataState> : ViewModel() {

    private val _state: MutableState<BaseScreenState<DataState>> = mutableStateOf(BaseScreenState.Loading)
    val state: State<BaseScreenState<DataState>> = _state

    private var currentJob: Job? = null

    fun onUIEvent(event: BaseEvent) {
        reduce(event)
    }

    /*
    * Данный метод предназначен для инитиализации стейт объекта
    * */
    protected abstract fun createDefaultDataState(): DataState

    /*
    * Возвращает состояние загрузки
    * */
    protected fun isLoading(): Boolean = _state.value is BaseScreenState.Loading

    protected open fun reduce(event: BaseEvent) {
        // Отработка базовых ивентов (В данный момент он пуст!)
    }

    protected fun getStateData(): DataState {
        return state.value.getResultData() ?: createDefaultDataState()
    }

    //Обновляет дата стейт объект используя текущий стейт объект, в случай если он пуст, вызывается createDefaultDataState()
    protected fun updateDataWithState(work: (currentData: DataState) -> DataState) {
        val currentData = (state.value as? BaseScreenState.Data)?.data ?: createDefaultDataState()
        val newState = BaseScreenState.Data(work(currentData))
        _state.value = newState
    }

    //Обновляет дата стейт объект
    protected fun updateData(newDataState: DataState) {
        _state.value = BaseScreenState.Data(newDataState)
    }

    protected fun <WorkResult> createRequest(
        request: suspend CoroutineScope.(currentData: DataState) -> WorkResult
    ): ViewModelRequest<DataState, WorkResult> {
        return ViewModelRequest(this, request)
    }

    protected fun <WorkResult> launchRequest(
        work: suspend CoroutineScope.(currentData: DataState) -> WorkResult,
        onLoading: () -> Unit,
        onData: (result: WorkResult, data: DataState) -> Unit,
        onError: ((reason: Throwable) -> Unit)?
    ): Job {
        currentJob?.cancel()
        return viewModelScope.launchWithCatch(
            block = {
                onLoading()
                val result = work(getStateData())
                onData(result, state.value.getResultData() ?: createDefaultDataState())
            },
            catch = { error ->
                onError?.invoke(error)
            }).also { currentJob = it }
    }

    protected fun onErrorDefaultReaction(reason: Throwable, onRetryAction: () -> Unit) {
        _state.value = BaseScreenState.Error(reason, onRetryAction)
    }

    protected class ViewModelRequest<DataState, WorkResult>(
        private val viewModel: BaseViewModel<DataState>,
        private val work: suspend CoroutineScope.(currentData: DataState) -> WorkResult
    ) {
        fun launch(
            onError: (Throwable) -> Unit = {},
            onLoading: () -> Unit = {
                viewModel._state.value = BaseScreenState.Loading
            },
            onData: ((result: WorkResult) -> Unit)
        ) {
            viewModel.launchRequest(
                work = work,
                onLoading = onLoading,
                onData = { result, _ -> onData(result) },
                onError = onError
            )
        }
    }
}
