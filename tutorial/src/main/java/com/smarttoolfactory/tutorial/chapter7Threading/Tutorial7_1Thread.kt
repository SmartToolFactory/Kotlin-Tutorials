package com.smarttoolfactory.tutorial.chapter7Threading

fun main() {
    // Threads run concurrently
    val myThread1 = MyThread()
    val myThread2 = MyThread()
    myThread1.start()
    myThread2.start()

    // Runnable can be passed to thread constructor
    val thread = Thread(MyRunnable())
    thread.start()

    val thread2 = Thread {
        // This is run method of this thread
        for (i in 0..10) {
            println("ANONYMOUS THREAD Count: $i, in thread: ${Thread.currentThread().name}")
            Thread.sleep(100)
        }
    }
    thread2.start()

    Thread.sleep(1000)
}

class MyThread : Thread() {
    override fun run() {
        for (i in 0..10) {
            println("THREAD Count: $i, in thread: ${Thread.currentThread().name}")
            sleep(100)
        }
    }
}

class MyRunnable : Runnable {
    override fun run() {
        for (i in 0..10) {
            println("RUNNABLE Count: $i, in thread: ${Thread.currentThread().name}")
            Thread.sleep(100)
        }
    }
}