package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

@InternalCoroutinesApi
fun main() = runBlocking<Unit> {

    /*
        🔥 Buffer
     */
//    exampleWithNoBuffer()
//    exampleWithBuffer()

    // 🔥 INFO Conflation
//    exampleConflate()

    // 🔥 Processing the latest value
//    exampleCollectLatest()

    /*
        🔥 Composing multiple flows
    */
    // 🔥 INFO Zip
//    exampleZip()
//    exampleZipWithDelay()
    // 🔥 INFO Combine
//    exampleCombine()
    // 🔥 INFO Merge
//    exampleMerge()

    /*
        🔥 Flattening flows
     */

    // 🔥 flatMapConcat -> RxJava ConcatMap
//    exampleFlatMapConcat()
    // 🔥 flatMapMerge -> RxJava FlatMap
    exampleFlatMapMerge()
    // 🔥 flatMapLatest -> RxJava SwitchMap
//    exampleFlatMapLatest()

    /*
        🔥 Flow exceptions
    */

    // 🔥 INFO Exception Transparency
//    exampleExceptionTransparency()
    // 🔥 INFO Transparent catch
//    exampleTransparentCatch()

    // 🔥 INFO Catching declaratively
//    exampleCatchingDecleratively()

    /*
       🔥 Flow Completion
     */

    // 🔥 INFO Imperative finally block
//    exampleFlowCompletionImperative()

    // 🔥 INFO  Declarative handling
//    exampleFlowCompletionDeclarative()

//    exampleFlowCompletionDeclarativeExceptions()


    /*
       🔥 Flow Cancelable
   */
//    exampleCancel()

//    exampleCancellable()

    // 🔥 INFO Custom interval
//    exampleCustomInterval()
}


/*
    🔥 Buffer
 */
fun flowSample(): Flow<Int> = flow {

    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }

}

private suspend fun exampleWithNoBuffer() {
//    val time = measureTimeMillis {
//
//        flowSample().collect {
//            delay(300) // pretend we are processing it for 300 ms
//            println(it)
//        }
//
//    }

    flowSample().collect {
        delay(300) // pretend we are processing it for 300 ms
        println(it)
    }

    println("Collected in  ms")

    /*
      It produces something like this, with the whole collection taking around
      1200 ms (three numbers, 400 ms for each):

      1
      2
      3
      Collected in 1220 ms
   */
}

suspend fun exampleWithBuffer() {

    val time = measureTimeMillis {
        flowSample()
            .buffer() // buffer emissions, don't wait
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
    }
    println("Collected in $time ms")

    /*
        It produces the same numbers just faster,
        as we have effectively created a processing pipeline,
        having to only wait 100 ms for the first number and
        then spending only 300 ms to process each number.
        This way it takes around 1000 ms to run
     */

    /*
        Prints:

        1
        2
        3
        Collected in 1169 ms
     */

}

// 🔥 INFO Conflation

/**
 * When a flow represents partial results of the operation or operation status updates,
 * it may not be necessary to process each value, but instead, only most recent ones.
 *
 * In this case, the conflate operator can be used to skip intermediate values
 * when a collector is too slow to process them.
 */
suspend fun exampleConflate() {

    val time = measureTimeMillis {
        flowSample()
            .conflate() // conflate emissions, don't process each one
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
    }

    println("Collected in $time ms")

    /*
        We see that while the first number was still being processed the second,
         and third were already produced, so the second one was conflated and
         only the most recent (the third one) was delivered to the collector:
     */

    /*
    Prints:

    1
    3
    Collected in 1012 ms

 */
}


/*
    🔥 Processing the latest value
 */

/**
 * Conflation is one way to speed up processing when both the emitter and collector are slow.
 *
 * It does it by dropping emitted values.
 * The other way is to cancel a slow collector and restart it every time a new value is emitted.
 *
 * There is a family of xxxLatest operators that perform the same essential
 * logic of a xxx operator, but cancel the code in their block on a new value.
 */
suspend fun exampleCollectLatest() {

    val time = measureTimeMillis {
        flowSample()

            .collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(300) // pretend we are processing it for 300 ms
                println("Done $value")
            }
    }
    println("Collected in $time ms")

    /*
        Since the body of collectLatest takes 300 ms, but new values are emitted every 100 ms,
         we see that the block is run on every value, but completes only for the last value:
     */

    /*
        Prints:

        Collecting 1
        Collecting 2
        Collecting 3
        Done 3
        Collected in 722 ms
     */

}

/*
    🔥 Composing multiple flows
 */

// 🔥 Zip
suspend fun exampleZip() {

    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print

    /*
        Prints:

        1 -> one
        2 -> two
        3 -> three

     */
}

suspend fun exampleZipWithDelay() {

    val nums = (1..3).asFlow()
        .onEach { delay(300) } // numbers 1..3 every 300 ms

    val strs = flowOf("one", "two", "three")
        .onEach { delay(400) } // strings every 400 ms

    val startTime = System.currentTimeMillis() // remember the start time

    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with "zip"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
        Prints:

        1 -> one at 556 ms from start
        2 -> two at 959 ms from start
        3 -> three at 1360 ms from start
     */

}

// 🔥 Combine
/**
 * When flow represents the most recent value of a variable or operation
 * it might be needed to perform a computation that depends on the most recent
 * values of the corresponding flows and to recompute it
 * whenever any of the upstream flows emit a value.
 * The corresponding family of operators is called **`combine`**.
 *
 * For example, if the numbers in the previous example update every 300ms,
 * but strings update every 400 ms, then zipping them using
 * the **`zip`** operator will still produce the same result,
 * albeit results that are printed every 400 ms:
 */
suspend fun exampleCombine() {

    val nums = (1..3).asFlow()
        .onEach { delay(300) } // numbers 1..3 every 300 ms

    val strs = flowOf("one", "two", "three")
        .onEach { delay(400) } // strings every 400 ms

    val startTime = System.currentTimeMillis() // remember the start time

    nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
        Prints:

        1 -> one at 427 ms from start
        2 -> one at 629 ms from start
        2 -> two at 827 ms from start
        3 -> two at 933 ms from start
        3 -> three at 1232 ms from start
     */

}

/*
    🔥 Flattening flows
 */
/**
 *
 * Flows represent asynchronously received sequences of values,
 * so it is quite easy to get in a situation where each value triggers
 * a request for another sequence of values.
 *
 * For example, we can have the following function that returns a flow of two strings 500 ms apart:
 */

suspend fun exampleWithoutFlattening() {
    (1..3).asFlow()
        .map {
            requestFlow(it)
        }

        .collect {
            // This is Flow<String> instead of result
        }

    /*
        Then we end up with a flow of flows (Flow<Flow<String>>) that needs to
        be flattened into a single flow for further processing.

        Collections and sequences have flatten and flatMap operators for this.
        However, due the asynchronous nature of flows they call for different modes of flattening,
         as such, there is a family of flattening operators on flows.
     */
}

// 🔥 INFO flatMapConcat (⚠️ RxJava concatMap)

/**
 * Concatenating mode is implemented by flatMapConcat and flattenConcat operators.
 * They are the most direct analogues of the corresponding sequence operators.
 * They wait for the inner flow to complete before starting to collect the next one
 */
suspend fun exampleFlatMapConcat() {

    val startTime = System.currentTimeMillis() // remember the start time

    (1..3).asFlow()
        .onEach { delay(100) } // a number every 100 ms
        .flatMapConcat { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
        Prints:

        1: First at 193 ms from start
        1: Second at 696 ms from start
        2: First at 797 ms from start
        2: Second at 1301 ms from start
        3: First at 1402 ms from start
        3: Second at 1902 ms from start
     */
}

// 🔥 INFO flatMapMerge (⚠️ RxJava flatMap)

/**
 * Another flattening mode is to concurrently collect all the incoming flows and merge
 * their values into a single flow so that values are emitted as soon as possible.
 *
 * It is implemented by flatMapMerge and flattenMerge operators.
 * They both accept an optional concurrency parameter that limits the number
 * of concurrent flows that are collected at the same
 * time (it is equal to DEFAULT_CONCURRENCY by default).
 */
suspend fun exampleFlatMapMerge() {

    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow()
        .onEach { delay(100) } // a number every 100 ms
        .flatMapMerge { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
        🔥🔥 Note that the flatMapMerge calls its block of code ({ requestFlow(it) }
        in this example) sequentially, but collects the resulting flows concurrently,
        it is the equivalent of performing a sequential map { requestFlow(it) }
         first and then calling flattenMerge on the result.
     */

    /*
        Prints:

        1: First at 218 ms from start
        2: First at 305 ms from start
        3: First at 408 ms from start
        1: Second at 719 ms from start
        2: Second at 808 ms from start
        3: Second at 914 ms from start
     */

}


// 🔥 INFO flatMapLatest (RxJava switchMap)

/**
 * In a similar way to the collectLatest operator. There is the corresponding "Latest" flattening
 * mode where a collection of the previous flow is cancelled as soon as new flow is emitted.
 *
 */
suspend fun exampleFlatMapLatest() {

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


fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First in thread ${Thread.currentThread().name}")
    delay(500) // wait 500 ms
    emit("$i: Second in thread ${Thread.currentThread().name}")
}

/*
    🔥 Flow exceptions
 */

// 🔥 INFO Exception Transparency

/**
 *
 * The emitter can use a catch operator that preserves this exception transparency and allows
 * encapsulation of its exception handling.
 * The body of the catch operator can analyze an exception and react to it in
 * different ways depending on which exception was caught:
 *
 * * Exceptions can be rethrown using throw.
 * * Exceptions can be turned into emission of values using emit from the body of catch.
 * * Exceptions can be ignored, logged, or processed by some other code.
 */
suspend fun exampleExceptionTransparency() {
    (1..3).asFlow()
        .map { value ->
            check(value <= 1) { "Crashed on $value" }
            "string $value"
        }
        .catch { e ->
            // 🔥 Emit exception here
            emit("Caught $e")
        } // emit on exception
        .collect { value -> println(value) }

    /*
        Prints:

        string 1
        Caught java.lang.IllegalStateException: Crashed on 2
     */
}

// 🔥 INFO Transparent Catch

/**
 * The catch intermediate operator, honoring exception transparency,
 * catches only upstream exceptions (that is an exception from all the operators above catch,
 * but not below it). If the block in collect { ... }
 * (placed below catch) throws an exception then it escapes:
 */
suspend fun exampleTransparentCatch() {

    sampleFlowWithException()
        .catch { e -> println("Caught $e") } // does not catch downstream exceptions
        .collect { value ->
            check(value <= 1) { "Collected $value" }
            println(value)
        }

    /*
        A "Caught …" message is not printed despite there being a catch operator:


     */

}


// 🔥 INFO Catching declaratively
suspend fun exampleCatchingDecleratively() {

    sampleFlowWithException()
        .onEach { value ->

            check(value <= 1) { "Collected $value" }
            println(value)
        }
        .catch { e -> println("Caught $e") }
//        .collect{
//            println("collect(): $it")
//        }
        .collect()


    /*
        Prints:

        Emitting 1
        1
        Emitting 2
        Caught java.lang.IllegalStateException: Collected 2
     */
}

fun sampleFlowWithException(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        check(i <= 1) { "Collected $i" }
        emit(i)
    }
}

/*
    🔥 Flow Completion
 */

// 🔥 INFO Imperative finally block
suspend fun exampleFlowCompletionImperative() {
    try {
        (1..3).asFlow().collect { value -> println(value) }
    } finally {
        println("Done")
    }

}

/**
 * For the declarative approach, flow has onCompletion intermediate operator that
 * is invoked when the flow has completely collected.
 * The previous example can be rewritten using
 * an onCompletion operator and produces the same output:
 */

// 🔥 INFO  Declarative handling
suspend fun exampleFlowCompletionDeclarative() {

    (1..3).asFlow()
        .onCompletion { println("Done") }
        .collect { value -> println(value) }


}

suspend fun exampleFlowCompletionDeclarativeExceptions() {

    // 🔥 Completes With onCompletion catching exception
    sampleFlowWithException()
        .onCompletion { cause -> if (cause != null) println("😱 Flow completed exceptionally") }
        .catch { cause -> println("🎃 Caught exception ${cause.message}") }
        .collect { value -> println(value) }

    /*
        Prints:
        Emitting 1
        1
        Emitting 2
        😱 Flow completed exceptionally
        🎃 Caught exception Collected 2
     */


    // 🔥 Throws exception because exception occurs in collect
//    sampleFlowWithException()
//        .onCompletion { cause -> println("Flow completed with $cause") }
//        .collect { value ->
//            check(value <= 1) { "Collected $value" }
//            println(value)
//        }
}

suspend fun exampleFlowCompletionDeclarativeExceptions2() {


    // 🔥 Completes With onCompletion catching exception
    (1..3).asFlow()
        .onCompletion { cause -> if (cause != null) println("😱 Flow completed exceptionally") }
        .map {

            if (it == 2) throw RuntimeException("Custom exception occurred")
            it
        }
        .catch { cause -> println("🎃 Caught exception ${cause.message}") }
        .collect { value -> println(value) }

    /*
        Prints:

        Emitting 1
        1
        Emitting 2
        😱 Flow completed exceptionally
        🎃 Caught exception Collected 2

     */

}

/*
    🔥 Flow cancellation checks
 */

private suspend fun CoroutineScope.exampleCancel() {
    flow {
        for (i in 1..5) {
            println("Emitting $i")
            emit(i)
        }
    }
        .cancellable()
        .collect { value ->
            if (value == 3) cancel()
            println(value)
        }

    /*
        Prints:

        Emitting 1
        1
        Emitting 2
        2
        Emitting 3
        3
        Emitting 4
        Exception in thread "main" kotlinx.coroutines.JobCancellationException: BlockingCoroutine was cancelled; job=BlockingCoroutine{Cancelled}@5d76b067

     */
}

private suspend fun CoroutineScope.exampleCancellable() {
    flow {
        for (i in 1..5) {
            println("Emitting $i")
            emit(i)
        }
    }
        .collect { value ->
            if (value == 3) cancel()
            println(value)
        }

    /*
        Prints:


     */

}

/*
    🔥 Custom Interval Implementation
 */
// Custom Interval implementation
private fun exampleCustomInterval() {

    val coroutineScope = CoroutineScope(SupervisorJob())

    val job = coroutineScope.launch {

        val jobInterval: Job = interval(1, TimeUnit.SECONDS)
            .onStart {
                emit(-1)
            }
            .onEach {
                println(it)
            }
            .map {
                "Current time $it"
            }
            .launchIn(coroutineScope)

        println("JobInterval $jobInterval")

    }

    println("Job: $job")

    sleep(5000)
}

fun interval(timeInMillis: Long, timeUnit: TimeUnit): Flow<Long> = flow {

    var counter: Long = 0

    val delayTime = when (timeUnit) {
        TimeUnit.MICROSECONDS -> timeInMillis / 1000
        TimeUnit.NANOSECONDS -> timeInMillis / 1_000_000
        TimeUnit.SECONDS -> timeInMillis * 1000
        TimeUnit.MINUTES -> 60 * timeInMillis * 1000
        TimeUnit.HOURS -> 60 * 60 * timeInMillis * 1000
        TimeUnit.DAYS -> 24 * 60 * 60 * timeInMillis * 1000
        else -> timeInMillis
    }

    while (true) {
        delay(delayTime)
        emit(counter++)
    }

}

