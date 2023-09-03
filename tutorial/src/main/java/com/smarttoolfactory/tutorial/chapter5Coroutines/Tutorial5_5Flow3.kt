@file:OptIn(ExperimentalCoroutinesApi::class)

package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking


@InternalCoroutinesApi
fun main() = runBlocking<Unit> {

//    mergeSample()
//    flatMapConcatSample()
    flatMapMergeSample()
//    flatmapLatestSample()

}


// 🔥 RxJava merge
private suspend fun mergeSample() {
    val flow1 = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon").asFlow()
    val flow2 = listOf("Zeta", "Eta", "Theta").asFlow()


    merge(flow1, flow2)
        .collect {
            println(it)
        }

    /*
        RxJava Counterpart

        val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        val source2 = Observable.just("Zeta", "Eta", "Theta")

        source1
        .mergeWith(source2)
        .doFinally {
        println("doOnFinally()")
        }
        .subscribe { i -> println("RECEIVED: $i") }
     */

    /*
        Prints:
        Beta
        Gamma
        Delta
        Epsilon
        Zeta
        Eta
        Theta
     */
}

// 🔥 RxJava concatMap
private suspend fun flatMapConcatSample() {
    (1..3).asFlow()
        .onEach { delay(100) } // a number every 100 ms
        .flatMapConcat { requestFlow(it) }
        .collect { value -> // collect and print
            println(value)
        }

    /*
        RxJava Counterpart
        observable1.concatMap{}

     */

    /*
        Prints
        1: First in thread main
        1: Second in thread main
        2: First in thread main
        2: Second in thread main
        3: First in thread main
        3: Second in thread main
     */
}

// 🔥 RxJava flatMap
private suspend fun flatMapMergeSample() {
    (1..3).asFlow()
        .onEach { delay(100) } // a number every 100 ms
        .flatMapMerge { requestFlow(it) }
        .collect { value -> // collect and print
            println(value)
        }

    /*
        RxJava Counterpart
        observable1.flatMap{}

     */

    /*
        Prints
        1: First in thread main
        2: First in thread main
        3: First in thread main
        1: Second in thread main
        2: Second in thread main
        3: Second in thread main
     */
}

// 🔥 RxJava switchMap
private suspend fun flatmapLatestSample() {

    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow()
//        .onEach { delay(100) } // a number every 100 ms
        .flatMapLatest { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
        Prints:

        1: First in thread main at 79 ms from start
        2: First in thread main at 84 ms from start
        3: First in thread main at 85 ms from start
        3: Second in thread main at 588 ms from start

     */

    /*
        🔥🔥 Note that flatMapLatest cancels all the code in
        its block ({ requestFlow(it) } in this example) on a new value.

        It makes no difference in this particular example,
        because the call to requestFlow itself is fast, not-suspending, and cannot be cancelled.
         However, it would show up if we were to use suspending functions like delay in there.
     */
}
