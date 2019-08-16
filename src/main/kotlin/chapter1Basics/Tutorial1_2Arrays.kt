package chapter1Basics

import chapter2OOP.MyClass
import chapter2OOP.User

fun main(args: Array<String>) {



    // INFO Initializing Arrays Method 1
    val numbers: IntArray = intArrayOf(1, 2, 3, 4, 5)
    println("Hey!! I am array Example numbers[2]: " + numbers[2])

    val myArray = IntArray(3);
    myArray[0] = 1
    myArray[1] = 2
    myArray[2] = 3

    // For loop
    for (i in myArray) {
        println("myArray i: $i")
    }


    // Iterator
    val iterator = myArray.iterator();

    while (iterator.hasNext()) {
        println("Item: ${iterator.nextInt()}")
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
    val stringArray = arrayOfNulls<String>(3)

    stringArray[0] = "Hello World"
    stringArray.forEach { s -> println("arrayOfNulls: $s") }

    // INFO Initializing Arrays Method 3
    // Creates an array where both items "Empty" with length of 4
    val anotherStringArray = Array(4) { "Empty" }
    anotherStringArray.forEach { s -> println("Another array: $s") }

    // INFO Initializing Arrays Method 4
    val sqrArray = IntArray(5) { x -> x * x }
    sqrArray.forEach { it -> println("Sqr : $it") }


    // INFO Other Initialization types
    val size = 10

    // Primitive arrays
    val arrayOfZeros = IntArray(6) //equivalent in Java: new int[size]
    val numbersFromOne = IntArray(6) { it + 1 }
    val myInts = intArrayOf(1, 1, 2, 3, 5, 8, 13, 21)

    // Non primitive-arrays
    val boxedInts = arrayOfNulls<Int>(size) //equivalent in Java: new Integer[size]
    val boxedZeros = Array(size) { 0 }

    val nulls = arrayOfNulls<String>(size) //equivalent in Java: new String[size]
    val strings = Array(size) { "n = $it" }
    val myStrings = arrayOf("foo", "bar", "baz")


    // 👍 This is valid
    boxedInts[0] = 12
    println("BoxedInt[0]: ${boxedInts[0]}")

    // 👍 This is valid
    nulls[0] = "Hey"
    println("nulls[0]: ${nulls[0]}")

    // WARNING 🔥 This is an EMPTY ARRAY with 0 length, no value can be assigned to this array. Returns ArrayIndexOutOfBoundsException
//    val emptyStringArray = arrayOf<String>()
//    emptyStringArray[0] = "TestStringConcatenation"


}