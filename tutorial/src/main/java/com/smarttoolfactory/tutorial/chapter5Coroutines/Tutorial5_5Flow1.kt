package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


fun main() = runBlocking {

    /*
        🔥 INFO Sequences
     */
//    exampleSequences().forEach {
//        println("Value from sequence: $it")
//    }

    /*
        🔥 INFO Flows
     */
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

    /*
        🔥 INFO Flows are cold
     */
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

    /*
        🔥 INFO Flow Cancellation
     */
//    withTimeoutOrNull(250) { // Timeout after 250ms
//        exampleFlowCancellation().collect { value -> println(value) }
//    }
//    println("Done")

    /*
        🔥 INFO Flow Builders asFlow
     */
//    (1..3).asFlow().collect { value -> println(value) }

    // 🔥 INFO map
//    exampleMap().collect()

    // 🔥 INFO transform
//    exampleTransform()

    /*
        🔥 Size-limiting operators
     */
//    numbers().collect {
//        println("Numbers $it")
//    }

    // 🔥 INFO take
//    exampleTake()
    // 🔥 INFO takeWhile
//    exampleTakeWhile()


    /*
        🔥 INFO Terminal Operators
     */

//    exampleReduce()
//    exampleFold()

    // 🔥 INFO Flows are sequential
//    exampleFlowsAreSequential()

    /*
        🔥 INFO Flow Context
     */
//    exampleFlowContext().collect {
//        println("In $this, value: $it")
//    }

    /*
      🔥 Flow context
    */

    // 🔥INFO Wrong Flow context
//    exampleFlowWrongEmissionWithContext().collect {
//        println("Flow with wrong context $it")
//    }

    // 🔥 flowOn operator
    exampleFlowOn()
            .collect {
                println("Collected in thread: ${Thread.currentThread().name}, value: $it")
            }
}

/*
    🔥 INFO Sequences
 */

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

/*
    🔥 INFO Flows
 */
fun exampleFlows(): Flow<Int> = flow { // flow builder

    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }

}

/*
    🔥 INFO Flows are cold
 */
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

/*
    🔥 INFO Flow cancellation
 */

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
   🔥 Intermediate flow operators
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

// 🔥 INFO transform
fun CoroutineScope.exampleTransform() {

    launch {

        (1..3).asFlow() // a flow of requests
                .transform { request ->
                    emit("Making request $request")
                    delay(100)
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
}

/*
    🔥 Size-limiting operators
 */

/**
 * Size-limiting intermediate operators like take cancel the execution
 * of the flow when the corresponding limit is reached.
 *
 * *Cancellation in coroutines is always performed by throwing an exception,
 * so that all the resource-management functions (like try { ... } finally { ... } blocks)
 * operate normally in case of cancellation:
 */
fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}

// 🔥 INFO take
fun CoroutineScope.exampleTake() {

    launch {

        (1..100).asFlow()
                .map {
                    delay(500)
                    it
                }
                .take(3)
                .collect {
                    println("Item: $it")
                }
    }

    /*
        Prints:

        Item: 1
        Item: 2
        Item: 3
     */
}

fun CoroutineScope.exampleTakeWhile() {
    launch {

        (1..100).asFlow()
                .map {
                    delay(500)
                    it
                }
                .takeWhile { it < 4 }
                .collect {
                    println("Item: $it")
                }

    }
}


/*
    🔥 Terminal operators
 */

/**
 *
 * Terminal operators on flows are suspending functions that start a collection of the flow.
 * The collect operator is the most basic one, but there are other terminal operators,
 * which can make it easier:
 *
 * * Conversion to various collections like toList and toSet.
 * * Operators to get the first value and to ensure that a flow emits a single value.
 * * Reducing a flow to a value with reduce and fold.
 *
 */
// 🔥 INFO reduce

fun CoroutineScope.exampleReduce() {

    launch {

        val sum = (1..5).asFlow()
//            .map { it * it } // squares of numbers from 1 to 5
                .reduce { a, b ->
                    println("in reduce: ${a + b}")
                    a + b
                } // sum them (terminal operator)
        println("Sum with reduce: $sum")

    }

    /*
        Prints:

        in reduce: 3
        in reduce: 6
        in reduce: 10
        in reduce: 15
        Sum with reduce: 15
     */

}

fun CoroutineScope.exampleFold() {

    launch {

        val sum = (1..5).asFlow()
                .fold(0) { a, b ->
                    println("in fold: ${a + b}")
                    a + b
                }

        println("Sum with fold: $sum")

    }

    /*
        Prints:

        in fold: 1
        in fold: 3
        in fold: 6
        in fold: 10
        in fold: 15
        Sum with fold: 15
     */
}

/*
    🔥 Flows are sequential
 */

/**
 * Each individual collection of a flow is performed sequentially unless special
 * operators that operate on multiple flows are used.
 *
 * The collection works directly in the coroutine that calls a terminal operator.
 *
 * No new coroutines are launched by default. Each emitted value is processed by all
 * the intermediate operators from upstream to downstream and is
 * then delivered to the terminal operator after.
 */
fun CoroutineScope.exampleFlowsAreSequential() {

    launch {

        (1..5).asFlow()
                .filter {
                    println("Filter $it")
                    it % 2 == 0
                }
                .map {
                    println("Map $it")
                    "string $it"
                }.collect {
                    println("Collect $it")
                }

    }

    /*
        Prints:

        Filter 1
        Filter 2
        Map 2
        Collect string 2
        Filter 3
        Filter 4
        Map 4
        Collect string 4
        Filter 5
     */

}

/*
    🔥 Flow context
 */


fun exampleFlowContext(): Flow<Int> = flow {
    println("Started foo flow")
    for (i in 1..3) {
        emit(i)
    }
}

// 🔥 Wrong Emission with Context
fun exampleFlowWrongEmissionWithContext(): Flow<Int> = flow {

    // The WRONG way to change context for CPU-consuming code in flow builder
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            emit(i) // emit next value
        }
    }

    /*
        Prints:

        Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
        Flow was collected in [CoroutineId(1), "coroutine#1":BlockingCoroutine{Active}@5511c7f8, BlockingEventLoop@2eac3323],
        but emission happened in [CoroutineId(1), "coroutine#1":DispatchedCoroutine{Active}@2dae0000, DefaultDispatcher].
        Please refer to 'flow' documentation or use 'flowOn' instead
        at ...
     */

}

// 🔥 flowOn operator
fun exampleFlowOn(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it in CPU-consuming way
        println("Emitting in ${Thread.currentThread().name}")
        emit(i) // emit next value
    }

    /*
        Prints:

        Started foo flow
        In BlockingCoroutine{Active}@621be5d1, value: 1
        In BlockingCoroutine{Active}@621be5d1, value: 2
        In BlockingCoroutine{Active}@621be5d1, value: 3
        Emitting in DefaultDispatcher-worker-1
        Collected in thread: main, value: 1
        Emitting in DefaultDispatcher-worker-1
        Collected in thread: main, value: 2
        Emitting in DefaultDispatcher-worker-1
        Collected in thread: main, value: 3
     */

}.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

