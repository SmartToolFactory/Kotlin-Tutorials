package chapter1Basics

import java.lang.IllegalArgumentException
import kotlin.system.measureNanoTime

fun main() {

    val result = fibonacci(8)
    println("Fibonacci RECURSIVE Result: $result")

    val resultWithFor = fibonacciWithLoop(8)
    println("Fibonacci LOOP Result: $resultWithFor")


    val timeRecursive = measureNanoTime {
        fibonacci(30)
    }

    println("Fibonacci Recursive Time: $timeRecursive")


    val timeWithLoop = measureNanoTime {
        fibonacciWithLoop(30)
    }

    println("Fibonacci Loop Time: $timeWithLoop")

}

// Recursive function that is optimized with tailRec keyword
fun factorial(x: Int): Int {

    tailrec fun factTail(y: Int, z: Int): Int {
        println("FactTail x: $x, y: $y, z: $z")
        return if (y == 1) z else factTail(y - 1, y * z)
    }

    return factTail(x, 1)

}

fun fibonacci(index: Int): Int {


    tailrec fun fibonacciTail(count: Int): Int {
        return when {
            index < 0 -> throw IllegalArgumentException("Index cannot be lower than 0")
            index in 0..1 -> index
            else -> fibonacciTail(index - 1) + fibonacciTail(index - 2)
        }
    }
    return fibonacciTail(index)
}

fun fibonacciWithLoop(index: Int): Int {


    if (index == 0 || index == 1) return index

    // Low, High
    // Index    0,  1,  2,  3,  4,  5,  6
    // Result   0,  1,  1,  2,  3,  5,  8
    var result = 0

    var low = 0
    var high = 1

    for (i in 2..index) {
        result = low + high

        low = high
        high = result
    }

    return result
}