package section5Coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {

    // INFO 🔥 Cancelling coroutine execution
//    cancel()

    // INFO 🔥 Cancellation is cooperative
//    cancelCooperative()

    // INFO 🔥  Making computation code cancellable
//    cancelComputation()

    // INFO 🔥 Closing resources with finally
//    cancelWithTryAndFinally()

    // INFO 🔥 Run non-cancellable block
//    cancelNonCancelable()


    // INFO 🔥 Timeout
//    cancelTimeout()

    // INFO 🔥 Timeout or Null

    cancelTimeoutOrNull()
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
            println("Exception e: ${e.message}")
        } finally {
            println("I'm running finally")
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion println("main: Now I can quit.")

    // 🔥 This is called last
    println("main: Job canceled!")
}


// INFO 🔥  Making computation code cancellable
private suspend fun CoroutineScope.cancelComputation() {

    println("cancelComputation()")

    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0

        // WARNING 🔥 isActive is an extension property that is available inside the code of coroutine via CoroutineScope object.

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

}


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
}


// INFO 🔥 Cancelling coroutine execution
private suspend fun CoroutineScope.cancel() {

    println("cancel()")

    val job = launch {

        repeat(20) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")

    job.cancel() // cancels the job
//    job.join() // waits for job's completion

    println("main: Now I can quit.")
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