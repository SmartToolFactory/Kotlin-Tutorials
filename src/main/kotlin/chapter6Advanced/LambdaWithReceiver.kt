package chapter6Advanced

fun main() {

    testScopedFunctions()

}


/*
    Scoped Functions
 */

private fun testScopedFunctions() {
    val testString = "Hello"


    testString.letMe<String, Int> {
        it.toIntOrNull() ?: -1
    }

    testString.runMe {
    }


    testString.alsoMe {
    }

    testString.applyMe {
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


class Task(val date: Long, val description: String)

