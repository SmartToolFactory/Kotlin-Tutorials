package chapter6Advanced

fun main() {

    testScopedFunctions()

}


/*
    Scoped Functions
 */

private fun testScopedFunctions() {

     val testString = "Hello"


    val resLet = testString.letMe<String, Int> {
        it.toIntOrNull() ?: -1
    }

    val resRun = testString.runMe {
        toIntOrNull() ?: -1
    }


    val resAlso = testString.alsoMe {
    }

    val resApply = testString.applyMe {

    }

    withMe(testString) {
        toUpperCase()
        capitalize()
    }

}

fun <T, R> T.letMe(predicate: (T) -> R): R {
    return predicate(this)
}

fun <T, R> T.runMe(predicate: T.() -> R): R {
    return this.predicate()
}


fun <T> T.alsoMe(predicate: (T) -> Unit): T {
    predicate(this)
    return this
}

fun <T> T.applyMe(predicate: T.() -> Unit): T {
    this.predicate()
    return this
}

fun <T, R> withMe(receiver: T, predicate: (T).() -> R): R {
    return receiver.predicate()
}


class Task(val date: Long, val description: String)
