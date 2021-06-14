package com.smarttoolfactory.tutorial.chapte.chapter5Coroutines

import kotlinx.coroutines.*

fun main() {

    println("START")

    // 🔥 INFO With runBlocking
//    runBlocking {
//
//        println("Before suspend function")
//        val token = getToken()
//        println("After token thread: ${Thread.currentThread().name}")
//        println("Token: $token")
//
//    }

    /*
        Prints:
        START
        Before suspend function
        After token thread: main
        Token: token
        END
     */

    // 🔥INFO With lunch
//    GlobalScope.launch {
//        println("Before suspend function")
//        val token = getToken()
//        println("After token thread: ${Thread.currentThread().name}")
//        println("Token: $token")
//    }

    println("END")

    /*
        Prints:
        START
        END
        Before suspend function
        After token thread: DefaultDispatcher-worker-1
        Token: token
     */

    Thread.sleep(5000)

    // 🔥INFO Scope with exception
//    runBlocking {
//        coroutineScopeWithException()
//    }
}


suspend fun getToken(): String {
    delay(3000)
    return "token"
}

/**
 * If a child coroutine throws an exception parent cancels other coroutines and ends
 */
suspend fun coroutineScopeWithException() {

    val scope = CoroutineScope(CoroutineName("Parent"))

    // New job gets created if not provided explicitly
    if (scope.coroutineContext[Job] != null) {
        println("New job is created!")
    }

    scope.launch {

        launch(CoroutineName("Child-A")) {
            delay(100)
            println("${Thread.currentThread().name} throwing exception")
            throw RuntimeException("Test Execption")
        }

        launch(CoroutineName("Child-B")) {
            println("${Thread.currentThread().name} before exception...")
            delay(500)
            println("${Thread.currentThread().name} after exception...")
        }

    }
}

fun fib(n: Int): Long {

    return when (n) {
        0 -> 0L
        1 -> n.toLong()
        else -> fib(n - 1) + fib(n - 2)
    }

}