package chapter5Coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {

    // INFO 🔥 Sequential by default

    printNumsWithDelayOne()
    printNumsWithDelayTwo()

//    val time = measureTimeMillis {
//        val one = doSomethingUsefulOne()
//        val two = doSomethingUsefulTwo()
//        println("The answer is ${one + two}")
//    }
//
//    println("Completed in $time ms")


    // INFO 🔥 Concurrent using async
    val timeAsync = measureTimeMillis {

        // WARNING 🔥 async functions are executed on Main Thread
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $timeAsync ms")


    async { printNumsWithDelayOne() }
    async { printNumsWithDelayTwo() }
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
    println("doSomethingUsefulOne ${Thread.currentThread().name}")
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    println("doSomethingUsefulTwo ${Thread.currentThread().name}")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}