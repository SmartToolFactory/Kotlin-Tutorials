package com.smarttoolfactory.tutorial.chapter5Coroutines

import com.smarttoolfactory.tutorial.chapter4Functions.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@InternalCoroutinesApi
fun main() = runBlocking {
//    mutableStateFlowSample()

    stateInFlowSample()
    delay(15000)

}

/*
    StateFlow is a state-holder observable flow that emits the current and new state updates to
    its collectors. The current state value can also be read through its value property.
    To update state and send it to the flow, assign a new value to
    the value property of the MutableStateFlow class.
 */
private fun mutableStateFlowSample() {
    GlobalScope.launch {

        val mutableFlow = MutableStateFlow(0)
        mutableFlow.emit(1)

        launch {
            // This is first collector
            mutableFlow.collect {
                println("Collect1: $it")
            }

        }

        delay(100)
        mutableFlow.emit(2)
        delay(100)
        mutableFlow.emit(3)
        mutableFlow.value = 4

        launch {
            // This is second collector
            mutableFlow.collect {
                println("Collect2: $it")
            }
        }

        println("Value: ${mutableFlow.value}")
    }

//    sleep(2000)
}

private fun stateInFlowSample() {
    val coroutineScope = CoroutineScope(EmptyCoroutineContext)
    coroutineScope.launch {

        val numberFlow = flow {
            var i = 0
            while (i < 20) {
                emit(i)
                println("Emitting: $i")
                i++
                delay(200)
            }

        }
            .stateIn(
            scope = this,
            /*
                Sharing is started when the first subscriber appears, immediately
                stops when the last subscriber disappears (by default), keeping the
                replay cache forever (by default).
             */
//            started = SharingStarted.WhileSubscribed(
//                stopTimeoutMillis = 100,
//                replayExpirationMillis = 100
//            ),
        )

        delay(300)

        val job1 = launch {
            numberFlow
                .onCompletion { cause ->
                    println("🍏🍏 Collector completed $cause")
                }
                .collect {
                    println("🍏 Collect: $it")
                }
        }

        delay(400)
        val job2 = launch {
            numberFlow
                .onCompletion { cause ->
                    println("🍎🍎 Collector completed $cause")
                }
                .collect {
                    println("🍎 Collect: $it")
                }
        }

        delay(600)
        job1.cancelAndJoin()
        job2.cancelAndJoin()

        delay(400)
        launch {
            numberFlow.collect {
                println("😁 Collect: $it")
            }
        }
    }
}