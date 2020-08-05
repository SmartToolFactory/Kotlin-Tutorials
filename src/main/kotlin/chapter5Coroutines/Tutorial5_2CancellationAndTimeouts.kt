package chapter5Coroutines

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() = runBlocking {

    // INFO 🔥 Cancelling coroutine execution
//    cancel()

    // INFO 🔥 Timeout
//    cancelTimeout()

    // INFO 🔥 Cancellation is cooperative
//    cancelCooperative()

    // INFO 🔥  Making computation code cancellable
//    cancelComputation()

    // INFO 🔥 Closing resources with finally
    cancelWithTryAndFinally()

    // INFO 🔥 Run non-cancellable block
//    cancelNonCancelable()

    // INFO 🔥 Timeout or Null

//    cancelTimeoutOrNull()
}


// INFO 🔥 Cancelling coroutine execution
private suspend fun CoroutineScope.cancel() {

    println("cancel()")

    val job = launch {

        repeat(20) { i ->
            println("job: I'm sleeping $i ...thread: ${Thread.currentThread().name}")
            delay(500L)
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")

    job.cancel() // cancels the job
//    job.join() // waits for job's completion

    println("main: Now I can quit.")

    /*
        Prints:
        cancel()
        job: I'm sleeping 0 ...thread: main
        job: I'm sleeping 1 ...thread: main
        job: I'm sleeping 2 ...thread: main
        main: I'm tired of waiting!
        main: Now I can quit.
     */
}


/**
 * Coroutine cancellation is cooperative. A coroutine code has to cooperate to be cancellable.
 * All the suspending functions in kotlinx.coroutines are cancellable.
 * They check for cancellation of coroutine and throw CancellationException when cancelled.
 *
 * However, if a coroutine is working in a computation and does not check for cancellation,
 * then it cannot be cancelled
 */
// INFO 🔥 Cancellation is cooperative
private suspend fun CoroutineScope.cancelCooperative() {

    println("cancelCooperative()")

    // Prints I'm sleeping even after job.cancelAndJoin is invoked until job is done
//     WARNING 🔥 cancelAndJoin() does not cancel an infinite loop while(true)
    val startTime = System.currentTimeMillis()

    val job = launch(Dispatchers.Default) {

        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")

    /*
        🔥🔥 Does not quit after cancel method is called, because suspension point such us
        delay is not invoked

        Prints:
        cancelCooperative()
        I'm sleeping 0 ...
        I'm sleeping 1 ...
        I'm sleeping 2 ...
        main: I'm tired of waiting!
        I'm sleeping 3 ...
        I'm sleeping 4 ...
        main: Now I can quit.

     */
}

/**
 * There are two approaches to making computation code cancellable.
 * The first one is to periodically invoke a suspending function that checks for cancellation.
 * There is a yield function that is a good choice for that purpose.
 * The other one is to explicitly check the cancellation status. Let us try the latter approach.
 *
 * Replace while (i < 5) in the previous example with while (isActive) and rerun it.
 */
// INFO 🔥  Making computation code cancellable
private suspend fun CoroutineScope.cancelComputation() {

    println("cancelComputation()")

    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0

        // WARNING 🔥 isActive is an extension property that is available inside
        // the code of coroutine via CoroutineScope object.

        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion println("main: Now I can quit.")

    /*
        Prints:
        cancelComputation()
        I'm sleeping 0 ...
        I'm sleeping 1 ...
        I'm sleeping 2 ...
        main: I'm tired of waiting!
     */

}

/**
 * Cancellable suspending functions throw CancellationException on cancellation
 * which can be handled in the usual way.
 *
 * For example, try {...} finally {...} expression
 * and Kotlin use function execute their finalization actions normally when a coroutine is cancelled
 */
// INFO 🔥 Closing resources with finally{
private suspend fun CoroutineScope.cancelWithTryAndFinally() {

    println("cancelWithTryAndFinally()")
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } catch (e: Exception) {
            println("Exception: $e")
        } finally {
            println("I'm running finally")
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion println("main: Now I can quit.")

    // 🔥 This is called last
    println("main: Job canceled!")

    /*
        Prints:
        cancelWithTryAndFinally()
        I'm sleeping 0 ...
        I'm sleeping 1 ...
        I'm sleeping 2 ...
        main: I'm tired of waiting!
      Exception: kotlinx.coroutines.JobCancellationException: StandaloneCoroutine was cancelled; job=StandaloneCoroutine{Cancelling}@3b95a09c
        I'm running finally
        main: Job canceled!
     */
}


// INFO 🔥 Timeout or Null
private suspend fun CoroutineScope.cancelTimeoutOrNull() {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result }
    }

    println("Result is $result")

}

/**
 * Any attempt to use a suspending function in the finally block
 * of the previous example causes CancellationException,
 * because the coroutine running this code is cancelled.
 *
 * Usually, this is not a problem,
 * since all well-behaving closing operations (closing a file, cancelling a job,
 * or closing any kind of a communication channel) are usually non-blocking
 * and do not involve any suspending functions.
 *
 * However, in the rare case when you need to suspend in a cancelled
 * coroutine you can wrap the corresponding code in withContext(NonCancellable) {...}
 * using withContext function and NonCancellable context
 */
// INFO 🔥 Run non-cancellable block
private suspend fun CoroutineScope.cancelNonCancelable() {

    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("I'm running finally")
                delay(1000L)
                println("I've just delayed for 1 sec because I'm non-cancellable\n")

            }
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion println("main: Now I can quit.")
}


// INFO 🔥 Timeout
private suspend fun CoroutineScope.cancelTimeout() {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}