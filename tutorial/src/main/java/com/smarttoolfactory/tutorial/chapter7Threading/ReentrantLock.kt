package com.smarttoolfactory.tutorial.chapter7Threading

import java.util.concurrent.locks.ReentrantLock

/**
 * 1) If Thread1 accesses lock before Thread2, since [ReentrantLock.lock] locks it multiple times,
 * Thread2 is dead locked.
 *
 * 2) If Thread2 accesses lock before Thread1, Thread2 access program main thread that is waiting
 * threads because of join ends successfully.
 */
fun main() {

    val lock = ReentrantLock()

    val thread1 = Thread {

        println(
            "1st START Lock in thread: ${Thread.currentThread().name}, " +
                    "lock held by current thread: ${lock.isHeldByCurrentThread}"
        )
        lock.lock()
        lock.lock()
        println(
            "1st GOT Lock in thread: ${Thread.currentThread().name}, " +
                    "lock held by this thread: ${lock.isHeldByCurrentThread}, " +
                    "hold count: ${lock.holdCount}, " +
                    "queue: ${lock.queueLength}"
        )
        Thread.sleep(4500)
        lock.unlock()
        println(
            "1st Unlock in thread: ${Thread.currentThread().name}, " +
                    "lock held by current thread: ${lock.isHeldByCurrentThread}, " +
                    "hold count: ${lock.holdCount}, " +
                    "queue: ${lock.queueLength}"
        )
    }

    val thread2 = Thread {
        println(
            "2nd START Lock in thread: ${Thread.currentThread().name}, " +
                    "lock held by current thread: ${lock.isHeldByCurrentThread}"
        )
        lock.lock()
        println(
            "2nd GOT Lock in thread: ${Thread.currentThread().name}, " +
                    "lock held by this thread: ${lock.isHeldByCurrentThread}, " +
                    "hold count: ${lock.holdCount}, " +
                    "queue: ${lock.queueLength}"
        )
        Thread.sleep(900)
        lock.unlock()
        println(
            "2nd Unlock in thread: ${Thread.currentThread().name}, " +
                    "lock held by current thread: ${lock.isHeldByCurrentThread}, " +
                    "hold count: ${lock.holdCount}, " +
                    "queue: ${lock.queueLength}"
        )
    }

//    logStates(thread1, thread2, lock)

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println("Program finishing")
}

private fun logStates(
    thread1: Thread,
    thread2: Thread,
    lock: ReentrantLock
) {
    val mainThread = Thread.currentThread()

    Thread {
        var counter = 0

        while (counter < 100) {

            println(
                "Thread1 state: ${thread1.state}, Thread2 state: ${thread2.state}, mainThread state: ${mainThread.state}, " +
                        "lock count for this thread: ${lock.holdCount}, queue length: ${lock.queueLength}"
            )
            Thread.sleep(50)
            counter++
        }
    }.start()
}
