package section4Functions

fun main(args: Array<String>) {

    val items = listOf(1, 2, 3, 4, 5)

    // INFO 🔥 High-order function

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
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// Function references can also be used for higher-order function calls:
    val product = items.fold(1, Int::times)

}

// INFO 🔥 High-order function
fun <T, R> Collection<T>.fold(initial: R,
                              combine: (acc: R, nextElement: T) -> R): R {

    var accumulator: R = initial

    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }

    return accumulator
}