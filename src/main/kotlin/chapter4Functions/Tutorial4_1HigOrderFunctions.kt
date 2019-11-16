package chapter4Functions

fun main() {


    // INFO 🔥 High-order functions

    val bigger = compare(2, 3) { x, y ->
        x > y
    }
    println("Bigger: $bigger")


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


