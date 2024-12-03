package com.app.agrilink.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Базовый интерфейс для ивентов.
 */
interface BaseEvent

/**
 * Интерфейс для жизненных циклов ивентов.
 */
interface LifecycleUIEvent : BaseEvent {
    object OnCreate : LifecycleUIEvent
    object OnStart : LifecycleUIEvent
    object OnResume : LifecycleUIEvent
    object OnPause : LifecycleUIEvent
    object OnStop : LifecycleUIEvent
}

/**
 * Метод для обработки событий жизненного цикла в Compose.
 *
 * @param onEvent Лямбда-функция, которая будет вызываться при каждом событии жизненного цикла.
 *                Принимает объект LifecycleOwner и событие Lifecycle.Event.
 */
@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    // Запоминаем актуальное состояние обработчика событий
    val eventHandler = rememberUpdatedState(onEvent)
    // Запоминаем актуального владельца жизненного цикла
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        // Получаем экземпляр жизненного цикла текущего владельца
        val lifecycle = lifecycleOwner.value.lifecycle

        // Создаем наблюдателя для событий жизненного цикла
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        // Добавляем наблюдателя к жизненному циклу
        lifecycle.addObserver(observer)

        // Убираем наблюдателя при уничтожении
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}