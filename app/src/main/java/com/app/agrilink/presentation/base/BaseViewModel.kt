package com.app.agrilink.presentation.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.app.agrilink.shared.util.launchWithCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<DataState> : ViewModel() {

    private val _baseState: MutableState<BaseScreenState<DataState>> = mutableStateOf(BaseScreenState.Loading)
    val baseState: State<BaseScreenState<DataState>> = _baseState

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
    protected fun isLoading(): Boolean = _baseState.value is BaseScreenState.Loading

    protected open fun reduce(event: BaseEvent) {
        // Отработка базовых ивентов (В данный момент он пуст!)
    }

    protected fun getStateData(): DataState {
        return baseState.value.getResultData() ?: createDefaultDataState()
    }

    //Обновляет дата стейт объект используя текущий стейт объект, в случай если он пуст, вызывается createDefaultDataState()
    protected fun updateDataWithState(work: (currentData: DataState) -> DataState) {
        val currentData = (baseState.value as? BaseScreenState.Data)?.data ?: createDefaultDataState()
        val newState = BaseScreenState.Data(work(currentData))
        _baseState.value = newState
    }

    //Обновляет дата стейт объект
    protected fun updateData(newDataState: DataState) {
        _baseState.value = BaseScreenState.Data(newDataState)
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
                onData(result, baseState.value.getResultData() ?: createDefaultDataState())
            },
            catch = { error ->
                onError?.invoke(error)
            }).also { currentJob = it }
    }

    protected fun onErrorDefaultReaction(reason: Throwable, onRetryAction: () -> Unit) {
        _baseState.value = BaseScreenState.Error(reason, onRetryAction)
    }

    protected class ViewModelRequest<DataState, WorkResult>(
        private val viewModel: BaseViewModel<DataState>,
        private val work: suspend CoroutineScope.(currentData: DataState) -> WorkResult
    ) {
        fun launch(
            onError: (Throwable) -> Unit = { reason ->
                viewModel._baseState.value = BaseScreenState.Error(reason)
            },
            onLoading: () -> Unit = {
                viewModel._baseState.value = BaseScreenState.Loading
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