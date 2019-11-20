package chapter6Advanced

import chapter6Advanced.SearchAlgorithms.Companion.binarySearch
import chapter6Advanced.SearchAlgorithms.Companion.binarySearchRecursive
import chapter6Advanced.SearchAlgorithms.Companion.jumpSearch
import chapter6Advanced.SearchAlgorithms.Companion.linearSearch
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

fun main() {


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

    // Jump Search
    val indexJump = jumpSearch(80, intArray)
    println("Index with JUMP search: $indexJump")


    /**** Popular Search ****/
//    val popularArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 7, 7, 7)
//    val popularItem = TestFunctions.searchMostPopularItem(popularArray)
//    println("Popular item: $popularItem")
}

class SearchAlgorithms {

    companion object {


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

            val itemIndex = -1

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


        /**
         * Time complexity O(sqrt(n))
         * Space complexity O(1)
         */
        fun jumpSearch(target: Int, array: Array<Int>): Int {

            val notFoundIndex = -1
            val arraySize = array.size

            // Target is not in this array
            if (array.isEmpty() || array[0] > target || array[arraySize - 1] < target) return notFoundIndex

            // Finding step to be jumped based on square root of the array
            val step = floor(sqrt(arraySize.toDouble())).toInt()

            // Left and right of the block
            var left = 0
            var right = 0

            // Check for the possible block left, right that target can be in

            // min is used if the last index is for example 8 but size is 10
            // So set last right of the block as last index of the array
            loop@ while (array[right] < target) {

                left = right

                right = min(arraySize - 1, right + step)

                if (array[right] >= target) {
                    break@loop
                }

            }

            // Target can either be left or right index, if one of them we don't have to loop
            if (array[right] == target) {
                return right
            } else if (array[left] == target) {
                return left
            }


            // 🔥 Linear Search
//            for (i in left..right) {
//                if (array[i] == target) return i
//            }

            // Or 🔥 Binary Search
            return binarySearchRecursive(target, left, right, array)

            return notFoundIndex
        }

        fun jumpSearch(integers: IntArray, elementToSearch: Int): Int {

            val arrayLength = integers.size
            var jumpStep = sqrt(arrayLength.toDouble()).toInt()
            var previousStep = 0

            while (integers[min(jumpStep, arrayLength) - 1] < elementToSearch) {
                previousStep = jumpStep
                jumpStep += jumpStep
                if (previousStep >= arrayLength)
                    return -1
            }

            // Search in block  from indexes between left index and minimum of right or last index
            while (integers[previousStep] < elementToSearch) {
                previousStep++
                if (previousStep == min(jumpStep, arrayLength))
                    return -1
            }

            return if (integers[previousStep] == elementToSearch) previousStep else -1
        }

        fun searchMostPopularItem(array: Array<Int>): Int {

            var maxCount = 0

            val map = HashMap<Int, Int>()

            // Check map if current element exits, if it so increase it by one, else put it
            array.forEach {
                if (map.containsKey(it)) {
                    var count = map[it]!! + 1
                    map[it] = count
                    if (count > maxCount) {
                        maxCount = count
                    }
                } else {
                    map[it] = 0
                }
            }


            val popularItems = map.entries.filter { mutableEntry ->
                mutableEntry.value == maxCount
            }.map {
                it.key
            }

            return popularItems.first()
        }
    }

}