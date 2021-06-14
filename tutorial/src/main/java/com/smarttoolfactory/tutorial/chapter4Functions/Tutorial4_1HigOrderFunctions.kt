package com.smarttoolfactory.tutorial.chapter4Functions

fun main() {

    // INFO 🔥 High-order functions
    val bigger = compare(2, 3) { x, y ->
        x > y
    }

    val list = listOf(1.1, 5.3, 3.4)
    list.max {
        println("MAX double: $it")
    }

    // INFO 🔥 High-order functions
    // This function uses lambda
    val compareLambda: (Int, Int) -> Boolean = { x, y ->
        x > y
    }

    val bigger2 = compare(2, 3, compareLambda)
    println("Bigger: $bigger, Bigger2 $bigger2")


    val strFirst = compareStrings("Zeta", "Alpha") { x, y ->
        x.first() > y.first()
    }
    println("strFirst: $strFirst")

    val strLength = compareStrings("Zeta", "Alpha") { x, y ->
        x.length > y.length
    }
    println("strLength: $strLength")

    // lengthCompare(): (String, String) -> Boolean = { x, y -> x.length > y.length }
    val strLength2 = compareStrings("Alpha", "Zeta", lengthCompare())
    println("strLength2: $strLength2")


    // INFO 🔥 List High-order function

    val items = listOf(1, 2, 3, 4, 5)

    // Lambdas are code blocks enclosed in curly braces.
    val result: Int = items.fold(0, {

        // When a lambda has parameters, they go first, followed by '->'
        // acc: R, nextElement: T      R: Int, T: Int
            acc: Int, nextElement: Int ->

        print("acc = $acc, i = $nextElement, ")
        val result: Int = acc + nextElement
        println("result = $result")
        // The last expression in a lambda is considered the return value:
        result
    })


// Parameter types in a lambda are optional if they can be inferred:
    val joinedToString = items.fold("Elements:", { acc, i -> "$acc $i" })

// Function references can also be used for higher-order function calls:
    val product = items.fold(1, Int::times)

    // High order function takes String and (Int) -> Int lambda function as parameter
    // Returns String to int or predefined result of lambda function
    // INFO 🔥 High-order function takes a LAMBDA function
    highOrderFun("3", lambdaFun())

    // INFO 🔥 High-order function takes a REGULAR function that returns LAMBDA function
    highOrderFun("3", nonLambdaFunction())


    val condition1 = 2 > 3

    // INFO 🔥 High-order function
    // this high-order function gets condition1 as Boolean, action1:() -> Unit and action2:()->Unit
    runWithCondition(
        condition1,
        {
            println("Action1 Invoked")
        },
        lambdaAction2()
    )

}

// INFO 🔥 High-order function
fun compare(num1: Int, num2: Int, action: (Int, Int) -> Boolean): Boolean {
    return action(num1, num2)
}

// INFO 🔥 High-order function
fun compareStrings(str1: String, str2: String, block: (String, String) -> Boolean): Boolean {
    return block(str1, str2)
}


// INFO 🔥 Lambda function
fun lengthCompare(): (String, String) -> Boolean = { x, y -> x.length > y.length }


// INFO 🔥 High-order function
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {

    var accumulator: R = initial

    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }

    return accumulator
}

fun <T : Number> List<T>.max(action: (T) -> Unit) {

    var max: Double = 0.0
    for (element: T in this) {
        element.toInt() > max
    }
        forEach {
        val number: Double = (it as? Double) ?: 0.0
        if (number > max) max = number
    }

    (max as? T)?.let {
        action(it)
    }
}

// INFO 🔥 High-order function
fun invokeAfterDelay(delayInMs: Long, predicateAfterDelay: () -> Unit) {
    println("STARTING DELAY FUNCTION")
    delay(delayInMs)
    predicateAfterDelay()
}


fun delay(timeInMillis: Long = 0) {
    Thread.sleep(timeInMillis)
}


// INFO 🔥 High-order function
fun highOrderFun(str: String, predicate: (String) -> Int): Int {
    return predicate(str)
}

// INFO 🔥 Lambda function
fun lambdaFun(): (String) -> Int = {
    it.toIntOrNull() ?: -1
}

// INFO 🔥🔥 NOT a lambda function, REGULAR function that returns a LAMBDA function
fun nonLambdaFunction(): (String) -> Int {
    return { s: String -> s.toIntOrNull() ?: -1 }
}

// INFO 🔥 High-order function
fun runWithCondition(condition: Boolean, action1: () -> Unit, action2: (() -> Unit)? = null) {
    if (condition) action1()
    else if (action2 != null) action2()
}

// INFO 🔥 Lambda Function
fun lambdaAction2(): () -> Unit = {
    println("Action2 Invoked")
}

