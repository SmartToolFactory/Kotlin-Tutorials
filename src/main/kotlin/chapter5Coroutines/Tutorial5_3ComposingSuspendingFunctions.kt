package chapter5Coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {

    // INFO 🔥 Sequential by default
    /*
        These 2 functions run sequentially, printNumsWithDelayTwo waits printNumsWithDelayOne
     */
//    printNumsWithDelayOne()
//    printNumsWithDelayTwo()

//    val time = measureTimeMillis {
//        val one = doSomethingUsefulOne()
//        val two = doSomethingUsefulTwo()
//        println("The answer is ${one + two}")
//    }
//
//    println("Completed in $time ms")

    /*
        Runs sequential

        Prints:
        doSomethingUsefulOne main
        doSomethingUsefulTwo main
        The answer is 42
       🔥🔥 Completed in 2015 ms

     */


    // INFO 🔥 Concurrent using async
//    val timeAsync = measureTimeMillis {
//
//        // WARNING 🔥 async functions are executed on Main Thread
//        val one = async { doSomethingUsefulOne() }
//        val two = async { doSomethingUsefulTwo() }
//        println("The answer is ${one.await() + two.await()}")
//    }
//    println("Completed in $timeAsync ms")

    /*
        Prints:
        doSomethingUsefulOne main
        doSomethingUsefulTwo main
        The answer is 42
        Completed in 1048 ms
     */


//    async { printNumsWithDelayOne() }
//    async { printNumsWithDelayTwo() }

    // 🔥 INFO Async-style functions
    // note that we don't have `runBlocking` to the right of `main` in this example
//    val time = measureTimeMillis {
//        // we can initiate async actions outside of a coroutine
//        val one = somethingUsefulOneAsync()
//        val two = somethingUsefulTwoAsync()
//        // but waiting for a result must involve either suspending or blocking.
//        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
//        runBlocking {
//            println("The answer is ${one.await() + two.await()}")
//        }
//    }
//    println("Completed in $time ms")

    // 🔥 Structured concurrency with async
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")

}


// INFO 🔥 Sequential by default

// Both methods run on main thread, and second method waits for first one execution to finish
suspend fun printNumsWithDelayOne() {

    repeat(100) {
        println("First function: ${it + 1} in thread: ${Thread.currentThread().name}")
        delay(1L)
    }
}

suspend fun printNumsWithDelayTwo() {
    repeat(100) {
        println("Second function: ${it + 1} in thread: ${Thread.currentThread().name}")
        delay(1L)
    }

}

suspend fun doSomethingUsefulOne(): Int {
    println("doSomethingUsefulOne in thread: ${Thread.currentThread().name}")
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    println("doSomethingUsefulTwo in thread: ${Thread.currentThread().name}")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

// 🔥 INFO Async-style functions

/*
    We can define async-style functions that invoke doSomethingUsefulOne and doSomethingUsefulTwo asynchronously
    using the async coroutine builder with an explicit GlobalScope reference.
     We name such functions with the "…Async" suffix to highlight the fact that
     they only start asynchronous computation and one needs to use the resulting deferred value to get the result.
 */
// The result type of somethingUsefulOneAsync is Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// The result type of somethingUsefulTwoAsync is Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

// 🔥 Structured concurrency with async

/*
    Let us take the Concurrent using async example and extract a function that concurrently
    performs doSomethingUsefulOne and doSomethingUsefulTwo and returns the sum of their results.

    Because the async coroutine builder is defined as an extension on CoroutineScope,
    we need to have it in the scope and that is what the coroutineScope function provides:

    This way, if something goes wrong inside the code of the concurrentSum function and
    it throws an exception, all the coroutines that were launched in its scope will be cancelled.
 */

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}