package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.*

/*
    Coroutines always execute in some context represented by a value of the CoroutineContext type,
    defined in the Kotlin standard library.

    The coroutine context is a set of various elements. The main elements are the
    Job of the coroutine, and its dispatcher.
 */
fun main() {

    // 🔥 INFO Dispatchers and threads
//    dispatchersAndThreads()

    // 🔥 INFO Unconfined vs confined dispatcher
//    unconfinedVsConfinedDispatcher()

    // 🔥 INFO Job in the context
//    jobInTheContext()

    // 🔥 INFO Children of a coroutine
    childrenOfACoroutine()

    // 🔥 INFO Parental responsibilities
//    parentalResponsibilities()
}

/**
 * * The coroutine context includes a coroutine dispatcher (see CoroutineDispatcher)
 * that determines what thread or threads the corresponding coroutine uses for its execution.
 * The coroutine dispatcher can confine coroutine execution to a specific thread,
 * dispatch it to a thread pool, or let it run unconfined.
 *
 * * All coroutine builders like launch and async accept an optional CoroutineContext parameter
 * that can be used to explicitly specify the dispatcher for the new coroutine and other context elements.
 */
// 🔥 INFO Dispatchers and threads
fun dispatchersAndThreads() {

    runBlocking {

        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }

    }

    /*
        Prints:
        Unconfined            : I'm working in thread main
        Default               : I'm working in thread DefaultDispatcher-worker-1
        newSingleThreadContext: I'm working in thread MyOwnThread
        main runBlocking      : I'm working in thread main
     */

    /**
     * * When launch { ... } is used without parameters, it inherits the context (and thus dispatcher)
     * from the CoroutineScope it is being launched from. In this case, it inherits the context
     * of the main runBlocking coroutine which runs in the main thread.
     *
     * * Dispatchers.Unconfined is a special dispatcher that also appears to run in the main thread,
     * but it is, in fact, a different mechanism that is explained later.
     *
     * * The default dispatcher that is used when coroutines are launched in GlobalScope
     * is represented by Dispatchers.Default and uses a shared background pool of threads,
     * so launch(Dispatchers.Default) { ... } uses the same dispatcher as GlobalScope.launch { ... }.
     *
     * * newSingleThreadContext creates a thread for the coroutine to run.
     * A dedicated thread is a very expensive resource.
     * In a real application it must be either released, when no longer needed,
     * using the close function, or stored in a top-level variable and reused throughout the application.
     *
     */

}


/**
 * * The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread,
 * but only until the first suspension point.
 *
 * * After suspension it resumes the coroutine in the thread that is fully determined
 * by the suspending function that was invoked. The unconfined dispatcher is appropriate
 * for coroutines which neither consume CPU time nor update any shared data (like UI)
 * confined to a specific thread.
 *
 * * On the other side, the dispatcher is inherited from the outer CoroutineScope by default.
 * The default dispatcher for the runBlocking coroutine, in particular,
 * is confined to the invoker thread, so inheriting it has the effect of confining execution
 * to this thread with predictable FIFO scheduling.
 */
// 🔥 INFO Unconfined vs confined dispatcher
fun unconfinedVsConfinedDispatcher() {

    runBlocking {

        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            // Works in kotlinx.coroutines.DefaultExecutor thread after SUSPENSION point
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }

        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }

    }

    /*
        Prints:

        Unconfined      : I'm working in thread main
        main runBlocking: I'm working in thread main
        Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor 🔥🔥
        main runBlocking: After delay in thread main
     */
}

// 🔥 INFO Job in the context
fun jobInTheContext() {
    runBlocking {
        println("My job is ${coroutineContext[Job]}")
    }
    /*
        Prints:
        My job is BlockingCoroutine{Active}@6659c656

     */
}

/**
 * * When a coroutine is launched in the CoroutineScope of another coroutine,
 * it inherits its context via CoroutineScope.coroutineContext
 * and the Job of the new coroutine becomes a child of the parent coroutine's job.
 * When the parent coroutine is cancelled, all its children are recursively cancelled, too.
 *
 * * However, when GlobalScope is used to launch a coroutine,
 * there is no parent for the job of the new coroutine.
 * It is therefore not tied to the scope it was launched from and operates independently.
 */
// 🔥 INFO Children of a coroutine
fun childrenOfACoroutine() {

    runBlocking {

        // launch a coroutine to process some kind of incoming request
        val request = launch {

            // it spawns two other jobs, one with GlobalScope
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }

            // and the other inherits the parent context
            launch {
                try {
                    delay(100)
                    println("job2: I am a child of the request coroutine")

                } catch (e: Exception) {
                    println("Exception $e with message ${e.message} in scope $this")
                }

                // 🔥 This where it's canceled, if this block was in try then we could catch the CancellationException
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")

            }

        }

        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        println("main: Who has survived request cancellation?")

        /*
            Prints:
            job1: I run in GlobalScope and execute independently!
            job2: I am a child of the request coroutine
            job1: I am not affected by cancellation of the request
            main: Who has survived request cancellation?
         */

    }

}

/**
 * A parent coroutine always waits for completion of all its children.
 * A parent does not have to explicitly track all the children it launches,
 * and it does not have to use Job.join to wait for them at the end:
 */
// 🔥 INFO Parental responsibilities
fun parentalResponsibilities() {

    runBlocking {

        // launch a coroutine to process some kind of incoming request
        val request = launch {

            repeat(3) { i -> // launch a few children jobs

                launch {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                    println("Coroutine $i is done")
                }

            }

            println("request: I'm done and I don't explicitly join my children that are still active")
        }

        request.join() // wait for completion of the request, including all its children
        println("Now processing of the request is complete")
    }

    /*
        Prints:
        request: I'm done and I don't explicitly join my children that are still active
        Coroutine 0 is done
        Coroutine 1 is done
        Coroutine 2 is done
        Now processing of the request is complete
     */
}


