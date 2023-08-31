package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.*
import java.lang.Thread.sleep

@ExperimentalStdlibApi
suspend fun main() {

    // INFO 🔥 Global Scope
//    globalLaunch()

//    globalLaunchWithRunBlocking()

    // INFO 🔥 Join
//    globalLaunchWithJoin()

    // INFO 🔥 Structured Concurrency
    structuredConcurrency()


    // INFO 🔥 Scope Builder
//    scopeBuilder()

    // INFO 🔥 Extract Function Refactoring
//    extractFunctionRefactoring()

    // INFO 🔥 Coroutines ARE light-weight
//    coroutinesLightweight()

    // INFO 🔥 Global Coroutines are like daemon threads
//    coroutinesLikeDaemonThreads()

}


@ExperimentalStdlibApi
private fun globalLaunch() {

    println("globalLaunch()")

    GlobalScope.launch {

        println("globalLaunch() initialize...")
        // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println(
            "World! in thread${Thread.currentThread().name}, scope: $this" +
                    " dispatcher: ${this.coroutineContext[CoroutineDispatcher]}"
        ) // print after delay
    }

    println("Hello, ${Thread.currentThread().name}") // main thread continues while coroutine is delayed

    // WARNING 🔥 Does not print World if Thread.sleep() is not called
    sleep(2000L) // block main thread for 2 seconds to keep JVM alive

    /*
        Prints:
        globalLaunch()
        Hello, main
        globalLaunch() initialize...
        World! DefaultDispatcher-worker-1
     */
}

/**
 * The result is the same, but this code uses only non-blocking delay.
 *
 * The main thread invoking runBlocking blocks until the coroutine inside runBlocking completes.
 */
private fun globalLaunchWithRunBlocking() {

    println("globalLaunchWithRunBlocking()")

    GlobalScope.launch {
        // launch new coroutine in background and continue
        println("World! ${Thread.currentThread().name} BEFORE delay")
        delay(1000L)
        println("World! ${Thread.currentThread().name} AFTER delay")
    }

    println("Hello,") // main thread continues here immediately

    // Blocks the thread until current task inside is completed
    runBlocking {
        println("Inside runBlocking ${Thread.currentThread().name} BEFORE delay")
        // but this expression blocks the main thread
        delay(2000L) // ... while we delay for 2 seconds to keep JVM alive
        println("Inside runBlocking ${Thread.currentThread().name} AFTER delay")
    }

    println("End")

    /*
        runBlocking blocks main thread for 2 seconds and after that main thread prints End
        In the mean time GlobalScope block executes asynchronously

        Prints:
        globalLaunchWithRunBlocking()
        Hello,
        World! DefaultDispatcher-worker-1 BEFORE delay
        Inside runBlocking main BEFORE delay
        World! DefaultDispatcher-worker-1 AFTER delay
        Inside runBlocking main AFTER delay
        End
     */
}

// INFO 🔥 Join
/**
 * Now the result is still the same, but the code of the main coroutine is
 * NOT tied to the duration of the background job in any way.
 * Does NOT block main thread while waiting background job to complete.
 */
private suspend fun globalLaunchWithJoin() {

    println("globalLaunchWithJoin()")

    val job = GlobalScope.launch {
        // launch a new coroutine and keep a reference to its Job
        delay(3000L)
        println("World! ${Thread.currentThread().name}, scope: $this")
    }
    println("Hello,")
    println("One,")
    println("Two")
    job.join() // wait until child coroutine completes

    // Invoked just after thread joins
    println("After thread joins")

    /*
        Does not BLOCK main thread. prints One, Two while waiting GlobalScope.launch to complete

        Prints:
        globalLaunchWithJoin()
        Hello,
        One,
        Two
        World! DefaultDispatcher-worker-1
        After thread joins
     */
}


/**
 * Every coroutine builder, including runBlocking, adds an instance of CoroutineScope
 * to the scope of its code block.
 *
 * We can launch coroutines in this scope without having to join them explicitly,
 * because an outer coroutine (runBlocking in our example) does not complete
 * until all the coroutines launched in its scope complete
 */
// INFO 🔥 Structured Concurrency
// 🔥 Coroutine runs after delay, main thread runs first
fun structuredConcurrency() = runBlocking {
    // this: CoroutineScope

    println("structuredConcurrency() scope: $this")

    launch {
        // launch new coroutine in the scope of runBlocking
        delay(3000L) // runs after delay
        // main thread
        println("World! thread: ${Thread.currentThread().name}, scope: $this")
    }
    // Runs first
    println("Hello, thread: ${Thread.currentThread().name}")

    /*
        Prints:
        structuredConcurrency() scope: BlockingCoroutine{Active}@bebdb06
        Hello, thread: main
        World! thread: main, scope: StandaloneCoroutine{Active}@3d82c5f3
     */
}


/**
 * runBlocking and coroutineScope may look similar
 * because they both wait for its body and all its children to complete.
 *
 * The main difference between these two is that the
 * runBlocking method blocks the current thread for waiting,
 * while coroutineScope just suspends, releasing the underlying thread for other usages.
 *
 * Because of that difference, runBlocking is a regular function
 * and coroutineScope is a suspending function
 */
// INFO 🔥 Scope Builder
fun scopeBuilder() = runBlocking {

    // this: CoroutineScope

    launch {
        delay(200L)
        // 🔥🔥 Runs 2nd
        println("Task from runBlocking: thread ${Thread.currentThread().name}")
    }


    coroutineScope {

        // Creates a new coroutine scope

        launch {
            delay(500L)
            // 🔥🔥🔥 Runs 3rd
            println("Task from nested launch: thread ${Thread.currentThread().name}")
        }

        delay(100L)
        // 🔥 Runs 1st
        println("Task from coroutine scope: thread ${Thread.currentThread().name}") // This line will be printed before nested launch

    }

    // 🔥🔥🔥🔥 Runs last
    println("Coroutine scope is over") // This line is not printed until nested launch completes

    /*
        Prints:
        Task from coroutine scope: thread main
        Task from runBlocking: thread main
        Task from nested launch: thread main
        Coroutine scope is over
     */

}

// INFO 🔥 Extract Function Refactoring
fun extractFunctionRefactoring() = runBlocking {
    launch {
        doWorld()
    }
    println("Hello,")
}

// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

// INFO 🔥 Coroutines ARE light-weight
// Launches 100.000 coroutines and does not crash unlike Threads

fun coroutinesLightweight() = runBlocking {
    repeat(100_000) {
        // launch a lot of coroutines

        // WARNING 🔥 Works fine

        launch {
            delay(1000L)
            println("Current thread: ${Thread.currentThread().name}")
        }

        // WARNING 🔥 Crashes with OutOfMemoryError Exception

//        Thread(Runnable() {
//            Thread.sleep(1000L)
//            print(".")
//        }).start()
    }
}


// INFO 🔥 Global Coroutines are like daemon threads

suspend fun coroutinesLikeDaemonThreads() {
    GlobalScope.launch {

        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            println("Thread: ${Thread.currentThread().name}")
            delay(500L)
        }
    }

    delay(1300L) // just quit after delay
}

