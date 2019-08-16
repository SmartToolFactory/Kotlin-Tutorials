package chapter5Coroutines

import kotlinx.coroutines.*


suspend fun main() {

    // INFO 🔥 Global Scope
//    globalLaunch()

//    globalLaunchWithRunBlocking()

    // INFO 🔥 Join
    globalLaunchWithJoin()

    // INFO 🔥 Structured Concurrency
//    structuredConcurrency()


    // INFO 🔥 Scope Builder
    scopeBuilder()

    // INFO 🔥 Extract Function Refactoring
    extractFunctionRefactoring()

    // INFO 🔥 Coroutines ARE light-weight
    coroutinesLightweight()

    // INFO 🔥 Global Coroutines are like daemon threads
    coroutinesLikeDaemonThreads()


}

private fun globalLaunch() {

    println("globalLaunch()")

    GlobalScope.launch {

        println("globalLaunch() initialize...")
        // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World! ${Thread.currentThread().name}") // print after delay
    }

    println("Hello, ${Thread.currentThread().name}") // main thread continues while coroutine is delayed

    // WARNING 🔥 Does not print World if Thread.sleep() is not called
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

private fun globalLaunchWithRunBlocking() {

    println("globalLaunchWithRunBlocking()")

    GlobalScope.launch {
        // launch new coroutine in background and continue
        delay(1000L)
        println("World! ${Thread.currentThread().name}")
    }

    println("Hello,") // main thread continues here immediately

    runBlocking {
        // but this expression blocks the main thread
        delay(2000L) // ... while we delay for 2 seconds to keep JVM alive
    }
}

// INFO 🔥 Join
private suspend fun globalLaunchWithJoin() {

    println("globalLaunchWithJoin()")

    val job = GlobalScope.launch {
        // launch a new coroutine and keep a reference to its Job
        delay(3000L)
        println("World! ${Thread.currentThread().name}")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
//sampleEnd

    // Invoked just after thread joins
    println("After thread joins")
}


// INFO 🔥 Structured Concurrency
// 🔥 Coroutine runs after delay, main thread runs first
fun structuredConcurrency() = runBlocking {
    // this: CoroutineScope

    println("structuredConcurrency()")

    launch {
        // launch new coroutine in the scope of runBlocking
        delay(3000L) // runs after delay
        println("World! thread: ${Thread.currentThread().name}")
    }
    // Runs first
    println("Hello, thread: ${Thread.currentThread().name}")
}


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
            print(".")
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

