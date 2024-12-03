package com.app.agrilink.shared.util

import com.google.firebase.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun Job.track(
    state: (isWorking: Boolean) -> Unit
): Job {
    state(isActive)
    this.invokeOnCompletion {
        state(false)
    }
    return this
}

fun CoroutineScope.launchWithCatch(
    dispatcher: CoroutineContext = Dispatchers.Main,
    catch: (suspend (Throwable) -> Unit)? = null,
    cancel: ((isInterrupted: Boolean) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(dispatcher) {
        try {
            block()
        } catch (th: Throwable) {
            if (BuildConfig.DEBUG) {
                th.printStackTrace()
            }

            if (th !is CancellationException) {
                catch?.invoke(th)
            } else {
                cancel?.invoke(th is JobInterruptedException)
            }
        }
    }
}

fun <T> CoroutineScope.asyncNullable(
    block: suspend CoroutineScope.() -> T
): Deferred<T?> {
    return async {
        try {
            block()
        } catch (th: Throwable) {
            if (BuildConfig.DEBUG) {
                th.printStackTrace()
            }
            null
        }
    }
}

internal class JobInterruptedException: CancellationException()
