package com.smarttoolfactory.tutorial.chapter1Basics


fun main() {

    // INFO Initializing Arrays Method 1
    val numbers: IntArray = intArrayOf(1, 2, 3, 4, 5)
    println("Hey!! I am array Example numbers[2]: " + numbers[2])

    // Creates a new array of the specified size, with all elements initialized to zero.
    val myArray: IntArray = IntArray(3)
    myArray[0] = 1
    myArray[1] = 2
    myArray[2] = 3

    // For loop
    for (i in myArray) {
        println("myArray i: $i")
    }

    // Iterator
    val iterator = myArray.iterator()

    while (iterator.hasNext()) {
        println("Item: ${iterator.nextInt()}")
    }

    iterator.forEach {
        println("Item: $it")
    }

    // Array's foreach operator
    myArray.forEach { i: Int -> println("Foreach i: $i") }

    // Set value of item in index2 to 15
    myArray.set(2, 15) // myArray[2] = 15
    println("MyArray " + myArray.get(2))

    // Get average of values in array
    println("MyArray average: " + myArray.average())

    // INFO Initializing Arrays Method 2
    // 🔥 WARNING This is array with 3 null elements
    val stringArray: Array<String?> = arrayOfNulls<String>(3)

    stringArray[0] = "Hello World"
    stringArray.forEach { s -> println("arrayOfNulls: $s") }

    // INFO Initializing Arrays Method 3
    // Creates an array where both items "Empty" with length of 4
    val anotherStringArray: Array<String> = Array(4) { "Empty" }
    anotherStringArray.forEach { s -> println("Another array: $s") }

    // INFO Initializing Arrays Method 4
    val sqrArray: IntArray = IntArray(5) { x -> x * x }
    sqrArray.forEach { it -> println("Sqr : $it") }


    // INFO Other Initialization types
    val size = 10

    // Primitive arrays
    val arrayOfZeros = IntArray(6) //equivalent in Java: new int[size]
    val numbersFromOne = IntArray(6) { it + 1 }
    val myInts: IntArray = intArrayOf(1, 1, 2, 3, 5, 8, 13, 21)

    // Alternative 1 for loop
    for (i in 0 until myInts.size) {
        println("i: ${myInts[i]}")
    }
    // Alternative 2 for loop
    for (element in myInts) {
        println("i: $element")
    }

    // Non primitive-arrays
    val boxedInts: Array<Int?> = arrayOfNulls<Int>(size) // 🔥 equivalent in Java: new Integer[size]
    val boxedZeros: Array<Int> = Array(size) { 0 }

    val nulls: Array<String?> = arrayOfNulls<String>(size) // 🔥 equivalent in Java: new String[size]
    val strings: Array<String> = Array(size) { "n = $it" }
    val myStrings: Array<String> = arrayOf("foo", "bar", "baz")

    // 👍 This is valid
    boxedInts[0] = 12
    println("BoxedInt[0]: ${boxedInts[0]}")

    // 👍 This is valid
    nulls[0] = "Hey"
    println("nulls[0]: ${nulls[0]}")

    // WARNING 🔥 This is an EMPTY ARRAY with 0 length, no value can be assigned to this array.
    // Returns ArrayIndexOutOfBoundsException
    val emptyStringArray: Array<String> = arrayOf<String>()
//    emptyStringArray[0] = "TestStringConcatenation"


    // 🔥 2D Arrays
    val coordinates: Array<Array<Int>> = arrayOf(
        arrayOf(1, 2),
        arrayOf(2, 3),
        arrayOf(3, 4),
        arrayOf(4, 5),
        arrayOf(5, 6),
        arrayOf(6, 7)
    )

    val arr = Array<Array<Int>>(6) { arrayOf(it) }
    arr[0] = arrayOf(1, 2)
    arr[1] = arrayOf(2, 3)
    arr[2] = arrayOf(3, 4)
    arr[3] = arrayOf(4, 5)
    arr[4] = arrayOf(5, 6)
    arr[5] = arrayOf(6, 7)

    coordinates.forEach {
        println("Array in array: $it")
    }

    val solution = Solution()

    solution.checkStraightLine(coordinates)
}


class Solution {

    fun checkStraightLine(coordinates: Array<Array<Int>>): Boolean {

        val y2 = coordinates[1][1].toDouble()
        val x2 = coordinates[1][0].toDouble()
        val y1 = coordinates[0][1].toDouble()
        val x1 = coordinates[0][0].toDouble()

        var m = 0.0

        if (y2 != y1) {
            m = ((y2 - y1) / (x2 - x1))
        }

        for (i in 2 until coordinates.size) {
            val y = coordinates[i][1] - coordinates[i - 1][1]
            val x = coordinates[i][0] - coordinates[i - 1][0]

            if (x == 0) {
                return false
            } else if (m != (y / x).toDouble()) {
                return false
            }
        }

        return true
    }
}