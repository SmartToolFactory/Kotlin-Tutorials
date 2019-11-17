package chapter1Basics

import chapter1Basics.TestFunctions.Companion.binarySearch
import chapter1Basics.TestFunctions.Companion.binarySearchRecursive
import chapter1Basics.TestFunctions.Companion.factorialIterative
import chapter1Basics.TestFunctions.Companion.linearSearch
import java.lang.IllegalArgumentException


fun main() {



//    /**** FACTORIAL ****/
//    val factRes = factorialIterative(5)
//    println("factRes: $factRes")
//    val factResult = factorialTailRecursive(5)
//    println("Factorial result: $factResult")
//
//    /**** FIBONACCI ****/
//    val fiboResult = fibonacci(8)
//    println("Fibonacci RECURSIVE Result: $fiboResult")
//
//    val fiboResultLoop = fibonacciWithLoop(8)
//    println("Fibonacci LOOP Result: $fiboResultLoop")
//
//
//    val timeFiboRecursive = measureNanoTime {
//        fibonacci(30)
//    }
//
//    println("Fibonacci Recursive Time: $timeFiboRecursive")
//
//    val timeFiboWithLoop = measureNanoTime {
//        fibonacciWithLoop(30)
//    }
//
//    println("Fibonacci Loop Time: $timeFiboWithLoop")
//
//
//    /**** PALINDROME ****/
//    val resPalindromeString = palindrome("ABCCBA")
//    println("Palindrome: $resPalindromeString")
//
//    val resPalindromeRecursive = palindromeRecursive("ABCCBA")
//
//    println("Palindrome Recursive: $resPalindromeRecursive")

    /**** SEARCH ****/

    val intArray = arrayOf(1, 2, 4, 6, 9, 11, 15, 23, 30, 32, 38, 40, 49, 56, 57, 67, 80, 83, 88, 89, 94, 99, 101, 200)

    // Linear Search
    val indexLinear = linearSearch(80, intArray)
    println("Index with linear search: $indexLinear")

    // Binary Search with iteration
    val indexBinary = binarySearch(80, intArray)
    println("Index with binary search: $indexBinary")
    // Recursive Binary Search
    val indexBinaryRecursive = binarySearchRecursive(80, 0, intArray.size - 1, intArray)
    println("Index with RECURSIVE binary search: $indexBinaryRecursive")


}

class TestFunctions {

    companion object {


        fun factorialIterative(num: Int): Int {

            if (num == 0 || num == 1) return 1
            var result = 1

            for (i in 2..num) {
                result *= i
            }

            return result

        }


        /**
         * *** FACTORIAL ***
         */
        // Recursive function that is optimized with tailRec keyword
        fun factorialTailRecursive(x: Int): Int {

            tailrec fun factTail(y: Int, z: Int): Int {
                println("FactTail x: $x, y: $y, z: $z")
                return if (y == 1) z else factTail(y - 1, y * z)
            }

            return factTail(x, 1)

        }


        /**
         * *** FIBONACCI ***
         */


        /**
         * Time complexity O(2^n)
         * Space complexity O(n)
         */
        fun fibonacci(index: Int): Int {


            return when {
                index < 0 -> throw IllegalArgumentException("Index cannot be lower than 0")
                index in 0..1 -> index
                else -> fibonacci(index - 1) + fibonacci(index - 2)
            }

        }

        /**
         * Time complexity O(n)
         * Space complexity O(1) only result field is used for storing output
         */
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


        /**
         * *** PALINDROME ***
         */


        /**
         * Time complexity O(n)
         * Space complexity O(1)
         */
        fun palindrome(text: String): Boolean {

            val charArray = text.toCharArray()

            val size = charArray.size

            var n = 0
            charArray.forEachIndexed { index, c ->
                if (c != charArray[size - index - 1]) return false
                n++
            }

            println("O(n) = $n")

            return true

        }


        /**
         * Time complexity O(n)
         * Space complexity O(n), if implemented recursively,
         * it needs to store the call to the method on a stack. This may require O(n) space.
         */
        fun palindromeRecursive(text: String): Boolean {

            val length = text.length
            if (length <= 1) return true

            if (text.first() == text.last()) {
                return palindromeRecursive(text.substring(1, length - 1))
            }

            return false
        }


        fun palindromeInt(number: Int): Boolean {
            // Cases 1 0 1, 9 3 9, 12 21, 43534
            if (number < 0 || number.rem(10) == 0) return false
            return false
        }

        /**
         * *** SEARCH ***
         */

        /**
         * Time complexity O(n)
         * Space complexity O(1)
         */
        fun <T> linearSearch(target: T, array: Array<T>): Int {

            val itemIndex = -1
            array.forEachIndexed { index, item ->
                if (target == item) {
                    return index
                }
            }
            return itemIndex
        }

        /**
         * Time complexity O(logN)
         * Space complexity O(1)
         */
        fun binarySearch(target: Int, array: Array<Int>): Int {

            val itemIndex = -1

            // 6 elements array size
            var rightIndex = array.size - 1 // 5
            var leftIndex = 0

            // Looking for 11, we should check if middle index is equals to target
            // 0, 1, 2, 3, 4,  5
            // 1, 2, 5, 8, 11, 12

            while (leftIndex <= rightIndex) {

                val middleIndex = (leftIndex + rightIndex) / 2

                println("BinarySearch -> Left: $leftIndex, Middle: $middleIndex, Right: $rightIndex, item = ${array[middleIndex]}")

                when {
                    // Target is equals to middle index
                    array[middleIndex] == target -> return middleIndex

                    // Item on middle index is smaller, get left index to right of the middle index
                    array[middleIndex] < target -> {
                        leftIndex = middleIndex + 1
                    }

                    // Item on middle index is bigger, get right index to left of middle index
                    else -> {
                        rightIndex = middleIndex - 1
                    }
                }
            }

            return itemIndex
        }


        /**
         * Time complexity O(logN)
         * Space complexity O(logN), if implemented recursively,
         * it needs to store the call to the method on a stack. This may require O(n) space.
         */
        fun binarySearchRecursive(target: Int, leftIndex: Int, rightIndex: Int, array: Array<Int>): Int {

            var itemIndex = -1

            while (leftIndex <= rightIndex) {
                val middleIndex = (rightIndex + leftIndex) / 2

                println("BinarySearch RECURSIVE -> Left: $leftIndex, Middle: $middleIndex, Right: $rightIndex, item = ${array[middleIndex]}")

                return when {
                    array[middleIndex] == target -> middleIndex
                    // Target is on the right, increase left by one and search again
                    array[middleIndex] < target -> {
                        binarySearchRecursive(target, middleIndex + 1, rightIndex, array)
                    }
                    // Target in on the left decrease right and search again
                    else -> {
                        binarySearchRecursive(target, leftIndex, middleIndex - 1, array)
                    }
                }

            }

            return itemIndex
        }
    }
}