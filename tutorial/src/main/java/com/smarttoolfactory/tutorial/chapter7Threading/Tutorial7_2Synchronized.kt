package com.smarttoolfactory.tutorial.chapter7Threading

fun main() {
    val nonSyncCounter = NonSyncCounter()
    val syncCounter = SyncedCounter()

    nonSyncCounter.count()
    syncCounter.count()
}

class NonSyncCounter {
    /**
     * Any thread can access and update this value while other thread might be reading
     * value that is not updated yet
     */
    private var counter: Int = 0

    fun count() {
        val thread1 = Thread {
            repeat(10000) {
                counter++
            }
        }

        val thread2 = Thread {
            repeat(10000) {
                counter++
            }
        }

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        println("NonSyncCounter counter: $counter")
    }
}

class SyncedCounter {
    private var counter: Int = 0

    /**
     * We acquire lock with SyncedCounter object this.
     * Only one thread at a time can access to counter
     */
    @Synchronized
    private fun increment() {
        counter++
    }


    fun count() {
        val thread1 = Thread {
            repeat(10000) {
                increment()
            }
        }

        val thread2 = Thread {
            repeat(10000) {
                increment()
            }
        }

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        println("SyncedCounter counter: $counter")
    }

}