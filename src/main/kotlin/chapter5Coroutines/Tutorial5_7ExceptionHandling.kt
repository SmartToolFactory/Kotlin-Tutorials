package chapter5Coroutines

import kotlinx.coroutines.*
import java.lang.Thread.sleep


/*
    Cancelled coroutine throws CancellationException in suspension points and
    that it is ignored by coroutines machinery.
 */
fun main() = runBlocking {

    // 🔥 INFO Exception propagation
//    exceptionPropagation()

    // 🔥 INFO CoroutineExceptionHandler
//    coroutineExceptionHandlerFun()

    // 🔥 INFO Cancellation and exceptions
//    cancellationAndExceptions()
//    cancellationAndExceptions2()
    cancellationAndExceptionsWithChildren()


}

/**
 * Exception that occurred inside [launch] function is propagated immediately
 * while exception in [async] requires [Deferred.await] to be called
 */
// 🔥 INFO Exception propagation
private suspend fun exceptionPropagation() {

    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")

    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }

    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }

    /*
        Prints:
        Throwing exception from launch
        Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
        Joined failed job
        Throwing exception from async
        Caught ArithmeticException
     */
}


/**
 * On JVM it is possible to redefine global exception handler for all coroutines
 * by registering CoroutineExceptionHandler via ServiceLoader.
 * Global exception handler is similar to Thread.defaultUncaughtExceptionHandler
 * which is used when no more specific handlers are registered.
 * On Android, uncaughtExceptionPreHandler is installed as a global coroutine exception handler.
 *
 * CoroutineExceptionHandler is invoked only on exceptions which are not
 * expected to be handled by the user,
 * so registering it in async builder and the like of it has no effect.
 */
// 🔥 INFO CoroutineExceptionHandler
private suspend fun coroutineExceptionHandlerFun() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }

    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }

    joinAll(job, deferred)

    /*
        Prints:
        Caught java.lang.AssertionError
     */

}


/**
 * Cancellation is tightly bound with exceptions.
 * Coroutines internally use **CancellationException** for cancellation,
 * these exceptions are ignored by all handlers,
 * so they should be used only as the source of additional debug information,
 * which can be obtained by catch block.
 *
 * When a coroutine is cancelled using Job.cancel, it terminates, but it does not cancel its parent.
 */
// 🔥 INFO Cancellation and exceptions
private suspend fun cancellationAndExceptions() {

    GlobalScope.launch {

        val job = launch {

            val child = launch {

                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }

            }

            yield()
            println("Cancelling child")
            child.cancel()
            child.join()
            yield()
            println("Parent is not cancelled")

        }

        job.join()
    }

}

/**
 * If a coroutine encounters an exception other than CancellationException,
 * it cancels its parent with that exception.
 *
 * This behaviour cannot be overridden and is used to provide stable coroutines hierarchies
 * for structured concurrency which do not depend on CoroutineExceptionHandler implementation.
 * The original exception is handled by the parent when all its children terminate.
 */
private suspend fun cancellationAndExceptions2() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(handler) {

        launch { // the first child

            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }

        }

        launch { // the second child
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()

    /*
        Prints:
        Second child throws an exception
        Children are cancelled, but exception is not handled until all children terminate
        The first child finished its non cancellable block
        Caught java.lang.ArithmeticException
     */


    /*
       🔥 If the second child throws CancellationException
       Prints:
       Second child throws an exception

     */

}


/**
 * Cancelling a child coroutine only finishes that coroutine.
 *
 * Throwing exception other than [CancellationException] in one child coroutine
 * cancels parent coroutine and parent coroutine cancels it's children.
 *
 * [CancellationException] only stops the coroutine that threw that exception
 */
private suspend fun cancellationAndExceptionsWithChildren() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }


    val job = GlobalScope.launch(handler) {

        println("Global scope: $this")


        val childJob1 = launch {

            println("Child1 scope: $this")

            var i = 0
            while (i < 5) {

                delay(500)
                println("🔥 Child1 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")

                /**
                 * Try canceling child
                 */
                // 🔥🔥🔥 Cancels only this child coroutine/coroutineScope
//                if (i == 2) cancel()

                /**
                 * Try exceptions
                 */
                // 🔥🔥🔥 If throws exception other than CancellationException,
                // parent and parent cancels other coroutines
//                if (i == 1) throw RuntimeException()

                // 🔥🔥🔥 Only cancels this coroutine
                if (i == 1) throw CancellationException()

                i++

            }

        }


        val childJob2 = launch {
            var i = 0
            while (i < 5) {
                delay(500)
                println("🥶 Child2 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
                i++
            }

        }

        val childJob3 = launch {
            var i = 0
            while (i < 5) {
                delay(500)
                println("🎃 Child3 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
                i++
            }
        }

        println("Job1: $childJob1, Job2: $childJob2, Job3: $childJob3")

//        delay(1300)
//        childJob1.cancel()
//        delay(300)
//        childJob3.cancel()

    }

    sleep(5000L) // delay a bit

    println("END")

    /*
        If childJob1 and childJob3 are canceled seqentially

       Prints:
       Global scope: StandaloneCoroutine{Active}@121dd6b9
       Child1 scope: StandaloneCoroutine{Active}@467ef6ba
       Job1: StandaloneCoroutine{Active}@467ef6ba, Job2: StandaloneCoroutine{Active}@b19736, Job3: StandaloneCoroutine{Active}@45d36d21
       🔥 Child1 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@467ef6ba
       🎃 Child2 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🔥 Child1 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@467ef6ba
       🎃 Child3 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child2 thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🎃 Child2 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🥶 Child3 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@b19736
       END
    */


    /*
        If RUNTIME Exception is thrown

        Prints:
        Global scope: StandaloneCoroutine{Active}@28aa9dd9
        Child1 scope: StandaloneCoroutine{Active}@575e6fef
        Job1: StandaloneCoroutine{Active}@575e6fef, Job2: StandaloneCoroutine{Active}@4a222520, Job3: StandaloneCoroutine{Active}@ccbb181
        🔥 Child1 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@575e6fef
        🥶 Child2 #0, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@4a222520
        🎃 Child3 #0, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@ccbb181
        🔥 Child1 #1, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@575e6fef
        🥶 Child2 #1, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@4a222520
        Caught java.lang.RuntimeException
        END
     */

    /*
        Of CANCELLATION Exception is thrown

        Prints:
        Global scope: StandaloneCoroutine{Active}@4ba89cb
        Job1: StandaloneCoroutine{Active}@30df48e1, Job2: StandaloneCoroutine{Active}@1d99db1a, Job3: StandaloneCoroutine{Active}@785a7917
        Child1 scope: StandaloneCoroutine{Active}@30df48e1
        🥶 Child2 #0, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@1d99db1a
        🔥 Child1 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@30df48e1
        🎃 Child3 #0, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@785a7917
        🥶 Child2 #1, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@1d99db1a
        🔥 Child1 #1, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@30df48e1
        🎃 Child3 #1, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@785a7917
        🥶 Child2 #2, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@1d99db1a
        🎃 Child3 #2, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@785a7917
        🥶 Child2 #3, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@1d99db1a
        🎃 Child3 #3, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@785a7917
        🥶 Child2 #4, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@1d99db1a
        🎃 Child3 #4, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@785a7917
        END
     */

}