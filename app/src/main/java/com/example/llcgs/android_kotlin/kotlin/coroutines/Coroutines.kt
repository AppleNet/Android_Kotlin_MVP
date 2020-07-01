package com.example.llcgs.android_kotlin.kotlin.coroutines;

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

val scope = MainScope()

fun launch(context: CoroutineContext = EmptyCoroutineContext,
                          start: CoroutineStart = CoroutineStart.DEFAULT,
                          block: suspend CoroutineScope.() -> Unit) {
    scope.launch(context, start, block)
}


fun <T> async(context: CoroutineContext = EmptyCoroutineContext,
              start: CoroutineStart = CoroutineStart.DEFAULT,
              block: suspend CoroutineScope.() -> T): Deferred<T> {
    return scope.async(context, start, block)
}

fun cancel() {
    scope.cancel()
}