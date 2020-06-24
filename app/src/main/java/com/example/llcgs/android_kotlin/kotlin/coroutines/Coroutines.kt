package com.example.llcgs.android_kotlin.kotlin.coroutines;

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun launch(context: CoroutineContext = EmptyCoroutineContext,
                          start: CoroutineStart = CoroutineStart.DEFAULT,
                          block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.launch(context, start, block)
}


fun <T> async(context: CoroutineContext = EmptyCoroutineContext,
              start: CoroutineStart = CoroutineStart.DEFAULT,
              block: suspend CoroutineScope.() -> T): Deferred<T> {
    return GlobalScope.async(context, start, block)
}