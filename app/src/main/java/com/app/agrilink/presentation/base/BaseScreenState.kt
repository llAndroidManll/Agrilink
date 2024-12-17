package com.app.agrilink.presentation.base

/**
 * Запеченная (sealed) классовая структура, представляющая состояния экрана.
 * @param T Тип данных, который может быть представлен в состоянии данных.
 */
sealed class BaseScreenState<out T> {

    /**
     * Состояние загрузки, указывающее на то, что данные в процессе получения.
     */
    data object Loading : BaseScreenState<Nothing>()

    /**
     * Состояние, представляющее успешно полученные данные.
     *
     * @param data Данные типа [T].
     */
    data class Data<out T>(val data: T) : BaseScreenState<T>()

    /**
     * Состояние ошибки, указывающее на проблему при получении данных.
     *
     * @param reason Причина ошибки, представленная объектом [Throwable].
     * @param retryAction Лямбда-функция, которая будет вызвана для повторной попытки действия.
     */
    data class Error(val reason: Throwable, val retryAction: (() -> Unit)? = null) : BaseScreenState<Nothing>()

    /**
     * Метод для получения данных из состояния.
     *
     * @return Данные типа [T] или null, если состояние не является [Data].
     */
    fun getResultData(): T? = (this as? Data<T>)?.data
}