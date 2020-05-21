package chapter5Coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    // 🔥 INFO Sequences
//    exampleSequences().forEach {
//        println("Value from sequence: $it")
//    }

    // 🔥 INFO Flows
    // Launch a concurrent coroutine to check if the main thread is blocked
//    launch {
//        for (k in 1..3) {
//            println("I'm not blocked $k")
//            delay(100)
//        }
//    }
//    // Collect the flow
//    exampleFlows().collect {value ->
//        println(value)
//    }

    // 🔥 INFO Flows are cold
//    println("Calling exampleFlowsCold...")
//    val flow = exampleFlowsCold()
//    println("Calling collect...")
//    flow.collect { value -> println(value) }
//    println("Calling collect again...")
//    flow.collect { value -> println(value) }

    /*
        Prints:

        Calling exampleFlowsCold...
        Calling collect...
        Flow started
        1
        2
        3
        Calling collect again...
        Flow started
        1
        2
        3
     */

    // 🔥 INFO Flow Cancellation
//    withTimeoutOrNull(250) { // Timeout after 250ms
//        exampleFlowCancellation().collect { value -> println(value) }
//    }
//    println("Done")

    // 🔥 INFO Flow Builders asFlow
//    (1..3).asFlow().collect { value -> println(value) }

    // 🔥 INFO map
//    exampleMap().collect()

    // 🔥 INFO transform
    (1..3).asFlow() // a flow of requests
        .transform { request ->
            emit("Making request $request")
            emit(performRequest(request))
        }
        .collect { response -> println(response) }

    /*
        Prints:

        Making request 1
        response 1
        Making request 2
        response 2
        Making request 3
        response 3
     */


}

// 🔥 INFO Sequences

/**
 * If we are computing the numbers with some CPU-consuming blocking code
 * (each computation taking 100ms), then we can represent the numbers using a Sequence:
 *
 * * This function uses suspend and blocks the [Thread] since it's using [Thread.sleep]
 */
fun exampleSequences(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it
        yield(i) // yield next value
    }
}

// 🔥 INFO Flows
fun exampleFlows(): Flow<Int> = flow { // flow builder

    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }

}

// 🔥 INFO Flows are cold
/**
 * Flows are cold streams similar to sequences — the code inside
 * a flow builder does not run until the flow is collected.
 *
 */
fun exampleFlowsCold(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

// 🔥 INFO Flow cancellation

/**
 * Flow adheres to the general cooperative cancellation of coroutines. However,
 * flow infrastructure does not introduce additional cancellation points.
 *
 * It is fully transparent for cancellation. As usual, flow collection
 * can be cancelled when the flow is suspended in a cancellable suspending function (like delay),
 * and cannot be cancelled otherwise.
 */
fun exampleFlowCancellation(): Flow<Int> = flow {

    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }

}


/*
    Intermediate flow operators
 */

// 🔥 INFO Map

fun exampleMap(): Flow<Int> = flow {

    (1..3).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }

}

suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}
