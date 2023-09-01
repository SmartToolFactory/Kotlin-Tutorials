package com.smarttoolfactory.tutorial.chapter5Coroutines

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Thread.sleep
import kotlin.coroutines.coroutineContext


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
//    cancellationWithChildren()
//    exceptionsWithChildren()

    // 🔥 Exceptions aggregation
//    exceptionsAggregation()
//    exceptionsAreTransparentAndUnwrapped()

    // 🔥 INFO Supervision
//    exceptionWithSupervisorJob()
    supervisionExceptionParentHandler()
//    supervisionExceptionParentHandler2()
//    supervisionExceptionAndCancellations()

    // 🔥 INFO Supervision Scope
//    supervisionScope()

    // 🔥INFO Exceptions in supervised coroutines
//    exceptionsInSupervisorScope()
//    exceptionsInSupervisorScope2()
}

/**
 * Exception that occurred inside [launch] function is propagated immediately
 * while exception in [async] requires [Deferred.await] to be called
 */
// 🔥 INFO Exception propagation
private suspend fun exceptionPropagation() {

    val job = GlobalScope.launch {
        println("Throwing exception from launch ${Thread.currentThread().name}")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job ${Thread.currentThread().name}")

    val deferred = GlobalScope.async {
        println("Throwing exception from async ${Thread.currentThread().name}")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }

    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException ${Thread.currentThread().name}")
    }

    /*
        Prints:
        Throwing exception from launch DefaultDispatcher-worker-1
        Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
        Joined failed job main
        Throwing exception from async DefaultDispatcher-worker-1
        Caught ArithmeticException main

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
        println("Caught $exception, in thread: ${Thread.currentThread().name}")
    }

    val job = GlobalScope.launch(handler) {
        println("Throwing exception in launch in thread: ${Thread.currentThread().name}")
        throw AssertionError()
    }

    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }

    joinAll(job, deferred)

    /*
        Prints:
        Throwing exception in launch in thread: DefaultDispatcher-worker-1
        Caught java.lang.AssertionError, in thread: DefaultDispatcher-worker-1
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

    /*
        Prints:
        Cancelling child
        Child is cancelled
        Parent is not cancelled

     */

}

/**
 * If a coroutine encounters an exception other than CancellationException,
 * it cancels its parent with that exception.
 *
 * This behaviour cannot be overridden and is used to provide stable coroutines hierarchies
 * for structured concurrency which do not depend on CoroutineExceptionHandler implementation.
 * The original exception is handled by the parent where causes all its children to terminate.
 */
private suspend fun cancellationAndExceptions2() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(handler) {

        val childJob1 = launch { // the first child

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

        val childJob2 = launch { // the second child
            delay(10)
            println("Second child throws an exception")
            // ⚠️ Alternative 1
            throw ArithmeticException()
            // ⚠️ Alternative 2
//            throw CancellationException()
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
 */
private suspend fun cancellationWithChildren() {

    val job = GlobalScope.launch {

        println("Global scope: $this")

        // First child job
        val childJob1 = launch {
            println("Child1 scope: $this")
            for (i in 0 until 5) {
                delay(500)
                println("🔥 Child1 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
            }
        }

        // Second child job
        val childJob2 = launch {
            for (i in 0 until 5) {
                delay(500)
                println("🥶 Child2 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
            }
        }

        // Third child job
        val childJob3 = launch {
            for (i in 0 until 5) {
                delay(500)
                println("🎃 Child3 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
            }
        }

        println("Job1: $childJob1, Job2: $childJob2, Job3: $childJob3")

        delay(1300)
        childJob1.cancel()
        delay(300)
        childJob3.cancel()

    }

    sleep(5000L) // delay a bit

    println("END")

    /*
        If childJob1 and childJob3 are canceled sequentially other child jobs continue to execute

       Prints:
       Global scope: StandaloneCoroutine{Active}@121dd6b9
       Child1 scope: StandaloneCoroutine{Active}@467ef6ba
       Job1: StandaloneCoroutine{Active}@467ef6ba, Job2: StandaloneCoroutine{Active}@b19736, Job3: StandaloneCoroutine{Active}@45d36d21
       🔥 Child1 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@467ef6ba
       🎃 Child2 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineSczope: StandaloneCoroutine{Active}@b19736
       🔥 Child1 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@467ef6ba
       🎃 Child3 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child2 thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🎃 Child2 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@45d36d21
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🥶 Child3 thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@b19736
       🥶 Child3 thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@b19736
       END
    */

}

/**
 *
 * Throwing exception other than [CancellationException] in one child coroutine
 * cancels parent coroutine and parent coroutine cancels it's children.
 *
 * [CancellationException] only stops the coroutine that threw that exception if there is
 * a [CoroutineExceptionHandler] used with job builder function, if NOT app CRASHES.
 */
private suspend fun exceptionsWithChildren() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(handler) {
        println("Global scope: $this, thread:${Thread.currentThread().name}")

        val childJob1 = launch {

            println("Child1 scope: $this")

            for (i in 0 until 5) {
                delay(500)
                println("🔥 Child1 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")

                // 🔥🔥🔥 If throws exception other than CancellationException,
                // parent is canceled and it cancels other coroutines
                if (i == 1) throw RuntimeException()
                // 🔥🔥🔥 Only cancels this coroutine
//                if (i == 1) throw CancellationException()
            }
        }

        val childJob2 = launch {
            try {
                for (i in 0 until 5) {
                    delay(500)
                    println("🥶 Child2 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
                }
            } catch (e: java.lang.Exception) {
                println("Child2 EXCEPTION: $e")
            }

        }

        val childJob3 = launch {
            try {
                for (i in 0 until 5) {
                    delay(500)
                    println("🎃 Child3 #$i, thread: ${Thread.currentThread().name}}, coroutineScope: $this")
                }
            } catch (e: Exception) {
                println("Child3 EXCEPTION: $e")
            }

        }

        println("Job1: $childJob1, Job2: $childJob2, Job3: $childJob3")

    }

    sleep(5000L) // delay a bit
    println("END")

    /*
        If RUNTIME Exception is thrown

        Prints:
        Global scope: StandaloneCoroutine{Active}@1652295b, thread:DefaultDispatcher-worker-1
        Child1 scope: StandaloneCoroutine{Active}@29e26151
        Job1: StandaloneCoroutine{Active}@29e26151, Job2: StandaloneCoroutine{Active}@51439646, Job3: StandaloneCoroutine{Active}@154f8258
        🥶 Child2 #0, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@51439646
        🎃 Child3 #0, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@154f8258
        🔥 Child1 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@29e26151
        🎃 Child3 #1, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@154f8258
        🔥 Child1 #1, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@29e26151
        🥶 Child2 #1, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@51439646
        Caught java.lang.RuntimeException
        END

        !!! IF Child2 and Child3 is IN TRY CATCH
        Prints:

        Global scope: StandaloneCoroutine{Active}@28045bbf, thread:DefaultDispatcher-worker-1
        Job1: StandaloneCoroutine{Active}@4d01fa9c, Job2: StandaloneCoroutine{Active}@36cbc390, Job3: StandaloneCoroutine{Active}@35a8a596
        Child1 scope: StandaloneCoroutine{Active}@4d01fa9c
        🔥 Child1 #0, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@4d01fa9c
        🎃 Child3 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@35a8a596
        🥶 Child2 #0, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@36cbc390
        🔥 Child1 #1, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@4d01fa9c
        🎃 Child3 #1, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@35a8a596
        🥶 Child2 #1, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@36cbc390
        Child2 EXCEPTION: kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=StandaloneCoroutine{Cancelling}@28045bbf
        Child3 EXCEPTION: kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=StandaloneCoroutine{Cancelling}@28045bbf
        Caught java.lang.RuntimeException
        END
     */

    /*
        If CANCELLATION Exception is thrown

        Prints:
        Global scope: StandaloneCoroutine{Active}@290e6301, thread:DefaultDispatcher-worker-1
        Child1 scope: StandaloneCoroutine{Active}@34216192
        Job1: StandaloneCoroutine{Active}@34216192, Job2: StandaloneCoroutine{Active}@6f310760, Job3: StandaloneCoroutine{Active}@6cefc607
        🔥 Child1 #0, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@34216192
        🎃 Child3 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@6cefc607
        🥶 Child2 #0, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@6f310760
        🔥 Child1 #1, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@34216192
        🎃 Child3 #1, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@6cefc607
        🥶 Child2 #1, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@6f310760
        🎃 Child3 #2, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@6cefc607
        🥶 Child2 #2, thread: DefaultDispatcher-worker-1}, coroutineScope: StandaloneCoroutine{Active}@6f310760
        🎃 Child3 #3, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@6cefc607
        🥶 Child2 #3, thread: DefaultDispatcher-worker-2}, coroutineScope: StandaloneCoroutine{Active}@6f310760
        🎃 Child3 #4, thread: DefaultDispatcher-worker-4}, coroutineScope: StandaloneCoroutine{Active}@6cefc607
        🥶 Child2 #4, thread: DefaultDispatcher-worker-3}, coroutineScope: StandaloneCoroutine{Active}@6f310760
        END

     */

}

/**
 * When multiple children of a coroutine fail with an exception the general rule is
 * "the first exception wins", so the first exception gets handled.
 *
 * All additional exceptions that happen after the first one are attached to the
 * first exception as suppressed ones.
 */
// 🔥 Exceptions aggregation
private suspend fun exceptionsAggregation() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
    }

    val job = GlobalScope.launch(handler) {

        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("First coroutine before ArithmeticException")
                throw ArithmeticException()
            }
        }

        launch {
            delay(100)
            println("Second coroutine before IOException")
            throw IOException()
        }
        delay(Long.MAX_VALUE)
    }
    job.join()

    /*
        Prints:
        Second coroutine before IOException
        First coroutine before ArithmeticException
        Caught java.io.IOException with suppressed [java.lang.ArithmeticException]

     */
}


//
private suspend fun exceptionsAreTransparentAndUnwrapped() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler caught original $exception")
    }

    val job = GlobalScope.launch(handler) {

        println("Outer scope: $this")

        val inner = launch {
            println("Middle scope: $this")

            launch {

                println("Inner scope: $this")

                launch {
                    println("Lowest scope: $this")
                    throw IOException()
                }
            }
        }

        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause: ${e.message}")
            throw e
        }
    }

    job.join()

    /*
        Prints:
        Outer scope: StandaloneCoroutine{Active}@1647223b
        Middle scope: StandaloneCoroutine{Active}@349b4315
        Inner scope: StandaloneCoroutine{Active}@354d26eb
        Lowest scope: StandaloneCoroutine{Active}@4faa740e

        Rethrowing CancellationException with original cause: StandaloneCoroutine is cancelling
        CoroutineExceptionHandler caught original java.io.IOException
     */

}

/**
 * * [SupervisorJob] let's parent coroutine to continue if a child coroutine throws an Exception
 *
 * * Whenever a child scope is created it does create a **NEW** [Job] instance instead of
 * inheriting from parent. Thus, **`childJob1`** should have it's own [SupervisorJob]
 * in [CoroutineScope.launch]
 * builder method to handle it's exception without propagating to parent.
 *
 * are NOT propagated to **parent**
 *  @see <a href="https://medium.com/androiddevelopers/coroutines-first-things-first-e6187bf3bb21">Cancellation and Exceptions in Coroutines</a>
 */

// 🔥 INFO Supervision
private suspend fun exceptionWithSupervisorJob() {

    /*
        Exception Handlers
     */
    val parentCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("PARENT CoroutineExceptionHandler Caught exception $exception")
    }

    val childCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("CHILD CoroutineExceptionHandler Caught exception $exception")
    }

    val myCoroutineScope =
        CoroutineScope(Job() + parentCoroutineExceptionHandler + Dispatchers.IO)

    println("START with scope: $myCoroutineScope, job: ${myCoroutineScope.coroutineContext[Job]}")

    val parentJob = myCoroutineScope.launch {
        println("Inside scope: $this, thread: ${Thread.currentThread().name}")

        // 🔥🔥This job should have it's own SupervisorJob.
        val firstChild =
            launch(SupervisorJob() + childCoroutineExceptionHandler) {

                for (i in 0 until 5) {
                    delay(500)
                    println("🔥 Child1 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                    if (i == 2) throw AssertionError("First child is cancelled")
                }
            }

        val secondChild = launch {
            try {
                for (i in 0 until 5) {
                    delay(500)
                    println("🥶 Child2 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                }
            } catch (e: Exception) {
                println("Child2 EXCEPTION: $e")
            }
        }
    }

    sleep(3000)
    println("END")

    /*

        ⚠️ If firs job uses launch(SupervisorJob() + childCoroutineExceptionHandler)
        Child CoroutineExceptionHandler catches exception, otherwise
        ParentCoroutineExceptionHandler catches the exception if it's added to parent scope.

        Prints:

        START with scope: kotlinx.coroutines.internal.ContextScope@45283ce2
        Inside scope: kotlinx.coroutines.internal.ContextScope@45283ce2, thread: main
        Inside scope: StandaloneCoroutine{Active}@228a0e79, thread: DefaultDispatcher-worker-1
        🥶 Child2 i: 0, scope: StandaloneCoroutine{Active}@689cf36a, Thread: DefaultDispatcher-worker-2
        🔥 Child1 i: 0, scope: StandaloneCoroutine{Active}@55390709, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 1, scope: StandaloneCoroutine{Active}@689cf36a, Thread: DefaultDispatcher-worker-3
        🔥 Child1 i: 1, scope: StandaloneCoroutine{Active}@55390709, Thread: DefaultDispatcher-worker-2
        🥶 Child2 i: 2, scope: StandaloneCoroutine{Active}@689cf36a, Thread: DefaultDispatcher-worker-3
        🔥 Child1 i: 2, scope: StandaloneCoroutine{Active}@55390709, Thread: DefaultDispatcher-worker-2
        CHILD CoroutineExceptionHandler Caught exception java.lang.AssertionError: First child is cancelled
        🥶 Child2 i: 3, scope: StandaloneCoroutine{Active}@689cf36a, Thread: DefaultDispatcher-worker-2
        🥶 Child2 i: 4, scope: StandaloneCoroutine{Active}@689cf36a, Thread: DefaultDispatcher-worker-2
        END
     */

}

private suspend fun supervisionExceptionParentHandler() {

    val supervisorJob = SupervisorJob()
    val myCoroutineScope = CoroutineScope(supervisorJob)

    // 🔥🔥🔥 Without a CoroutineExceptionHandler on parent Job exceptions in child jobs are not handled
    val parentCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("PARENT CoroutineExceptionHandler Caught exception $exception")
    }

    myCoroutineScope.launch(parentCoroutineExceptionHandler) {
        println("Inside scope: $this, thread: ${Thread.currentThread().name}")
        launch {
            for (i in 0 until 10) {
                delay(500)
                println("🔥 Child1 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                if (i == 2) throw AssertionError("First child is cancelled")
            }
        }

        launch {
            try {
                for (i in 0 until 10) {
                    delay(500)
                    println("🥶 Child2 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                }
            } catch (e: Exception) {
                println("Child2 EXCEPTION: $e")
            }
        }
    }

    sleep(4000)
    println("END")

    /*
        Prints:
        Inside scope: StandaloneCoroutine{Active}@3050456f, thread: DefaultDispatcher-worker-1
        🥶 Child2 i: 0, scope: StandaloneCoroutine{Active}@6dfd1ca0, Thread: DefaultDispatcher-worker-3
        🔥 Child1 i: 0, scope: StandaloneCoroutine{Active}@29d0bca, Thread: DefaultDispatcher-worker-2
        🥶 Child2 i: 1, scope: StandaloneCoroutine{Active}@6dfd1ca0, Thread: DefaultDispatcher-worker-2
        🔥 Child1 i: 1, scope: StandaloneCoroutine{Active}@29d0bca, Thread: DefaultDispatcher-worker-3
        🔥 Child1 i: 2, scope: StandaloneCoroutine{Active}@29d0bca, Thread: DefaultDispatcher-worker-2
        🥶 Child2 i: 2, scope: StandaloneCoroutine{Active}@6dfd1ca0, Thread: DefaultDispatcher-worker-3
        Child2 EXCEPTION: kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=StandaloneCoroutine{Cancelling}@3050456f
        PARENT CoroutineExceptionHandler Caught exception java.lang.AssertionError: First child is cancelled
     */
}


private suspend fun supervisionExceptionParentHandler2() {

    val supervisorJob = SupervisorJob()
    val myCoroutineScope = CoroutineScope(supervisorJob)

    // 🔥🔥🔥 Without a CoroutineExceptionHandler on parent Job exceptions in child jobs are not handled
    val parentCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("PARENT CoroutineExceptionHandler Caught exception $exception")
    }

    myCoroutineScope.launch(parentCoroutineExceptionHandler) {
        println("Inside scope: $this, thread: ${Thread.currentThread().name}")

        /*
            🔥🔥This job crashes since try-catch block does not recover from crash inside
            launch
         */

        try {
            launch {
                for (i in 0 until 10) {
                    delay(500)
                    println("🔥 Child1 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                    if (i == 2) throw AssertionError("First child is cancelled")
                }
            }

        } catch (e: Exception) {
            println("⚠️ First CHILD JOB crashed with ${e.message}, in thread: ${Thread.currentThread().name}")
        }


        /*
            🔥🔥🔥 If this launch uses SupervisorJob() it continues,
            and the parent coroutine waits for it to finish
            even after first child job crashes
         */
        launch(SupervisorJob()) {
            try {
                for (i in 0 until 10) {
                    delay(500)
                    println("🥶 Child2 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                }
            } catch (e: Exception) {
                println("Child2 EXCEPTION: $e")
            }
        }
    }

    sleep(4000)
    println("END")

    /*
        Prints:
        Inside scope: StandaloneCoroutine{Active}@170aad61, thread: DefaultDispatcher-worker-1
        🔥 Child1 i: 0, scope: StandaloneCoroutine{Active}@107a89fc, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 0, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-2
        🔥 Child1 i: 1, scope: StandaloneCoroutine{Active}@107a89fc, Thread: DefaultDispatcher-worker-2
        🥶 Child2 i: 1, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 2, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-2
        🔥 Child1 i: 2, scope: StandaloneCoroutine{Active}@107a89fc, Thread: DefaultDispatcher-worker-3
        Exception in thread "DefaultDispatcher-worker-3" java.lang.AssertionError: First child is cancelled
        🥶 Child2 i: 3, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 4, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 5, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-3
        🥶 Child2 i: 6, scope: StandaloneCoroutine{Active}@14facc9e, Thread: DefaultDispatcher-worker-3
        END

     */

}

/**
🔥🔥🔥 Adding a Job to [CoroutineScope] only effects that scope.
[CoroutineScope.launch] starts with a new job unless it's provided as a parameter
 */
private suspend fun supervisionExceptionAndCancellations() {

    val supervisor = SupervisorJob()
    val coroutineScope = CoroutineScope(coroutineContext + supervisor)

    // launch the first child
    val firstChild = coroutineScope.launch(CoroutineExceptionHandler { _, _ -> }) {
        println("First child is failing")
        throw AssertionError("First child is cancelled")
    }

    // launch the second child
    val secondChild = coroutineScope.launch {
        firstChild.join()

        // Cancellation of the first child is not propagated to the second child
        println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")

        try {
            delay(Long.MAX_VALUE)
        } finally {
            // But cancellation of the supervisor is propagated
            println("Second child is cancelled because supervisor is cancelled")
        }

    }

    // wait until the first child fails & completes
    firstChild.join()

    println("Cancelling supervisor")
    supervisor.cancel()
    secondChild.join()


    /*

        🔥🔥🔥 With JOB
        Prints:

        First child is failing
        Cancelling supervisor

        🔥🔥🔥 with SupervisorJob
        Prints:

        First child is failing
        First child is cancelled: true, but second one is still active
        Cancelling supervisor
        Second child is cancelled because supervisor is cancelled


     */
}

/**
 * For scoped concurrency supervisorScope can be used instead of
 * coroutineScope for the same purpose.
 *
 * * It propagates cancellation only in one direction and cancels all
 * children only if it has failed itself.
 *
 * * It also waits for all children
 * before completion just like coroutineScope does.
 */
// 🔥 INFO Supervision Scope
private suspend fun supervisionScope() {

    try {
        supervisorScope<Unit> {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught assertion error")
    }

    sleep(3000L)
    println("END")

    /*
        Prints:

        Child is sleeping
        Throwing exception from scope
        Child is cancelled
        Caught assertion error
        END

        🔥 IF yield is commented out
        Prints:

        Throwing exception from scope
        Caught assertion error
        END
     */
}

/**
 * Another crucial difference between regular and supervisor jobs is exception handling.
 * Every child should handle its exceptions by itself via exception handling mechanisms.
 * This difference comes from the fact that child's failure is not propagated to the parent.
 */
// 🔥INFO Exceptions in supervised coroutines
private suspend fun exceptionsInSupervisorScope() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler caught $exception, in thread: ${Thread.currentThread().name}")
    }

    supervisorScope {

        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }

        println("Scope is completing")

    }
    println("Scope is completed")

    /*
        Prints:

        Scope is completing
        Child throws an exception
        Caught java.lang.AssertionError
        Scope is completed
     */

}

private suspend fun exceptionsInSupervisorScope2() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler caught $exception, in thread: ${Thread.currentThread().name}")
    }

    supervisorScope {

        val child1 = launch(handler) {

            for (i in 0 until 10) {
                delay(200)
                println("🔥 Child1 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
                if (i == 2) throw AssertionError("First child is cancelled")
            }
        }

        val child2 = launch {
            for (i in 0 until 5) {
                delay(200)
                println("🥶 Child2 i: $i, scope: $this, Thread: ${Thread.currentThread().name}")
            }
        }
    }

    println("END")

    /*
        Prints:

        🔥 Child1 i: 0, scope: StandaloneCoroutine{Active}@64a294a6, Thread: main
        🥶 Child2 i: 0, scope: StandaloneCoroutine{Active}@7e0b37bc, Thread: main
        🔥 Child1 i: 1, scope: StandaloneCoroutine{Active}@64a294a6, Thread: main
        🥶 Child2 i: 1, scope: StandaloneCoroutine{Active}@7e0b37bc, Thread: main
        🔥 Child1 i: 2, scope: StandaloneCoroutine{Active}@64a294a6, Thread: main
        CoroutineExceptionHandler caught java.lang.AssertionError: First child is cancelled, in thread: main
        🥶 Child2 i: 2, scope: StandaloneCoroutine{Active}@7e0b37bc, Thread: main
        🥶 Child2 i: 3, scope: StandaloneCoroutine{Active}@7e0b37bc, Thread: main
        🥶 Child2 i: 4, scope: StandaloneCoroutine{Active}@7e0b37bc, Thread: main
        END
     */
}