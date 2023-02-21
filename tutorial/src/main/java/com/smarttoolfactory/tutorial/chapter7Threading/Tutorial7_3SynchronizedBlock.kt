@file:OptIn(ExperimentalTime::class, ExperimentalTime::class)

package com.smarttoolfactory.tutorial.chapter7Threading

import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun main() {
    /**
     * This one takes more than 4 seconds because we lock addToFirstList and
     * addSecondList with same lock, SingleLockedObject, instance(this) because of that
     * while lock is acquired while addingFirstList second list is locked as well
     */
//    val singleLockedObject = SingleLockedObject()
//    singleLockedObject.start()

    /**
     * Takes about 2.5s
     */
    val multipleLockedObject = MultipleLockedObject()
    multipleLockedObject.start()

}


class SingleLockedObject {
    private val list1 = mutableListOf<Int>()
    private val list2 = mutableListOf<Int>()

    @Synchronized
    private fun addToFirstList() {
        Thread.sleep(1)
        list1.add(Random.nextInt(100))
//        println("🍏 addToFirstList-> Thread: ${Thread.currentThread().name}")
    }

    @Synchronized
    private fun addToSecondList() {
        Thread.sleep(1)
        list2.add(Random.nextInt(100))
//        println("🍎 addToSecondList-> Thread: ${Thread.currentThread().name}")
    }

    fun start() {
        val time = measureTime {
            val thread1 = Thread {
                process()
            }
            val thread2 = Thread {
                process()
            }

            thread1.start()
            thread2.start()

            thread1.join()
            thread2.join()
        }

        println("😆 Total time: $time, list1: ${list1.size}, list2: ${list2.size}")

    }

    private fun process() {
        for (i in 0..999) {
            addToFirstList()
            addToSecondList()
        }
    }
}

class MultipleLockedObject {
    private val list1 = mutableListOf<Int>()
    private val list2 = mutableListOf<Int>()

    private val lock1 = Any()
    private val lock2 = Any()


    private fun addToFirstList() {
        /**
         * While this lock is acquired no other thread can run this but can run
         * other function with lock2
         */
        synchronized(lock1){
            Thread.sleep(1)
            list1.add(Random.nextInt(100))
//        println("🍏 addToFirstList-> Thread: ${Thread.currentThread().name}")
        }
    }

    private fun addToSecondList() {
        /**
         * While this lock is acquired no other thread can run this but can run
         * other function with lock1
         */
        synchronized(lock2){
            Thread.sleep(1)
            list2.add(Random.nextInt(100))
//        println("🍎 addToSecondList-> Thread: ${Thread.currentThread().name}")
        }
    }

    fun start() {
        val time = measureTime {
            val thread1 = Thread {
                process()
            }
            val thread2 = Thread {
                process()
            }

            thread1.start()
            thread2.start()

            thread1.join()
            thread2.join()
        }

        println("😆 Total time: $time, list1: ${list1.size}, list2: ${list2.size}")

    }

    private fun process() {
        for (i in 0..999) {
            addToFirstList()
            addToSecondList()
        }
    }
}