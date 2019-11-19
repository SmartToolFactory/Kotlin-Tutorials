package chapter6Advanced

import chapter1Basics.TestFunctions

fun main() {


    val intArray = arrayOf(1, 2, 4, 6, 9, 11, 15, 23, 30, 32, 38, 40, 49, 56, 57, 67, 80, 83, 88, 89, 94, 99, 101, 200)

    // Linear Search
    val indexLinear = TestFunctions.linearSearch(80, intArray)
    println("Index with linear search: $indexLinear")

    // Binary Search with iteration
    val indexBinary = TestFunctions.binarySearch(80, intArray)
    println("Index with binary search: $indexBinary")
    // Recursive Binary Search
    val indexBinaryRecursive = TestFunctions.binarySearchRecursive(80, 0, intArray.size - 1, intArray)
    println("Index with RECURSIVE binary search: $indexBinaryRecursive")


    /**** Popular Search ****/
    val popularArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 7, 7, 7)

    val popularItem = TestFunctions.searchMostPopularItem(popularArray)

    println("Popular item: $popularItem")
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

        fun jumpSearch(target: Int, array: Array<Int>): Int {

            var index = -1


            return index
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