package chapter6Advanced

import chapter6Advanced.SortUtils.Companion.bubbleSort
import chapter6Advanced.SortUtils.Companion.bubbleSortRecursive
import chapter6Advanced.SortUtils.Companion.generateRandomArray
import chapter6Advanced.SortUtils.Companion.insertionSort
import chapter6Advanced.SortUtils.Companion.mergeSort
import chapter6Advanced.SortUtils.Companion.printArray
import chapter6Advanced.SortUtils.Companion.quickSort
import chapter6Advanced.SortUtils.Companion.selectionSort
import java.lang.StringBuilder


/*
    Algorithm	Time Complexity
                        Best	        Average 	    Worst
    Selection Sort	    Ω(n^2)	        θ(n^2)	        O(n^2)
    Bubble Sort	        Ω(n)	        θ(n^2)	        O(n^2)
    Insertion Sort	    Ω(n)	        θ(n^2)	        O(n^2)
    Quick Sort	        Ω(n log(n))	    θ(n log(n))	    O(n^2)
    Merge Sort	        Ω(n log(n))	    θ(n log(n))	    O(n log(n))
    Heap Sort	        Ω(n log(n))	    θ(n log(n))	    O(n log(n))
    Bucket Sort     	Ω(n+k)	        θ(n+k)	        O(n^2)
    Radix Sort	        Ω(nk)	        θ(nk)	        O(nk)
 */

fun main() {

    var unsortedArray = intArrayOf(19, 14, 16, 7, 6, 4, 8, 12, 5, 9, 17)

    unsortedArray = generateRandomArray(10)


    val copyArray1 = unsortedArray.clone()
    val copyArray2 = unsortedArray.clone()
    val copyArray3 = unsortedArray.clone()

    // Bubble Sort
    val bubbleSortedArray = bubbleSort(unsortedArray)
    println("bubbleSortedArray: ${printArray(bubbleSortedArray)}")

    // Recursive
    unsortedArray = intArrayOf(19, 14, 16, 6, 4, 8, 12, 5, 9, 17)

    val bubbleSortRecursiveArray = bubbleSortRecursive(unsortedArray, unsortedArray.size)
    println("bubbleSortRecursiveArray: ${printArray(bubbleSortRecursiveArray)}")


    // Selection Sort
    unsortedArray = intArrayOf(19, 14, 16, 6, 4, 8, 12, 5, 9, 17)

    val selectionSortedArray = selectionSort(copyArray2)
    println("selectionSortedArray: ${printArray(selectionSortedArray)}")

    // Insertion Sort
    unsortedArray = intArrayOf(19, 14, 16, 6, 4, 8, 12, 5, 9, 17)

    val insertionSort = insertionSort(copyArray3)
    println("insertionSort: ${printArray(insertionSort)}")

    // * Merge Sort
    unsortedArray = intArrayOf(19, 14, 16, 6, 4, 8, 12, 5, 9, 17)
    val mergeSort = mergeSort(unsortedArray, 0, unsortedArray.size - 1)

    // Quick Sort
    unsortedArray = intArrayOf(19, 14, 16, 6, 4, 8, 12, 5, 9, 17)
    val quickSort = quickSort(unsortedArray, 0, unsortedArray.size - 1)
    println("quickSortedArray: ${printArray(unsortedArray)}")

}


class SortUtils {

    companion object {

        // {6, 7, 4, 8, 12, 5, 9}


        /**
         * Time Complexity:

        Best Case Sorted array as input. Or almost all elements are in proper place. [ O(N) ]. O(1) swaps.
        Worst Case: Reversely sorted / Very few elements are in proper place. [ O(N2) ] . O(N2) swaps.
        Average Case: [ O(N^2) ] . O(N^2) swaps.
        Space Complexity: A temporary variable is used in swapping [ auxiliary, O(1) ]. Hence it is In-Place sort.

        Advantage:
        It is the simplest sorting approach.
        Best case complexity is of O(N) [for optimized approach] while the array is sorted.
        Using optimized approach, it can detect already sorted array in first pass with time complexity of O(1).
        Stable sort: does not change the relative order of elements with equal keys.
        In-Place sort.

        Disadvantage:
        Bubble sort is comparatively slower algorithm.
         */
        fun bubbleSort(array: IntArray): IntArray {

            for (i in 0 until array.size) {

                // Check for each element until
                for (j in 0 until (array.size - 1 - i)) {
                    // Swap 2 elements with index i and i+1 and swap if item at index i is bigger than the one at i+1
                    if (array[j] > array[j + 1]) {
                        val temp = array[j]
                        array[j] = array[j + 1]
                        array[j + 1] = temp
                    }

                    printHorizontalArray(array, i, j)
                }

            }

            return array

        }

        fun bubbleSortRecursive(array: IntArray, length: Int): IntArray {

            if (length == 1) return array

            for (i in 0 until length - 1) {
                if (array[i] > array[i + 1]) {
                    val temp = array[i]
                    array[i] = array[i + 1]
                    array[i + 1] = temp
                }
            }

            return bubbleSortRecursive(array, length - 1)

        }


        /**
         * Time Complexity:

        Best Case [ O(N2) ]. Also O(N) swaps.
        Worst Case: Reversely sorted, and when inner loop makes maximum comparison. [ O(N2) ] . Also O(N) swaps.
        Average Case: [ O(N2) ] . Also O(N) swaps.
        Space Complexity: [ auxiliary, O(1) ]. In-Place sort.

        Advantage:
        It can also be used on list structures that make add and remove efficient,
        such as a linked list. Just remove the smallest element of unsorted part and end at the end of sorted part.
        Best case complexity is of O(N) while the array is already sorted.
        Number of swaps reduced. O(N) swaps in all cases.
        In-Place sort.

        Disadvantage:
        Time complexity in all cases is O(N2), no best case scenario.
         */
        fun selectionSort(array: IntArray): IntArray {

            val arraySize = array.size

            // I=N is ignored, Arr[N] is already at proper place.
            // array[1:(I-1)] is sorted sub array, Arr[I:N] is unordered sub array
            // smallest among { Arr[I], Arr[I+1], Arr[I+2], ..., Arr[N] } is at place min_index

            // One by one move boundary of unsorted sub array
            for (i in 0 until arraySize) {

                // 🔥 BUBBLE SORT
//                for (j in 0 until (array.size - 1 - i)) {
//                    if (array[j] > array[j + 1]) {
//                        val temp = array[j]
//                        array[j] = array[j + 1]
//                        array[j + 1] = temp
//                    }
//                }


                // 🔥 SELECTION SORT
                var min = i

                // Find the minimum element in unsorted array
                for (j in (i + 1) until arraySize) {
                    // Current element is smaller than minimum element
                    // Finds the index of the current smallest element for ith iteration
                    if (array[j] < array[min]) {
                        min = j
                    }
                }

                // Swap the found minimum element with the current(i) first element
                val temp = array[min]
                array[min] = array[i]
                array[i] = temp

            }
            return array
        }


        /**
         * Time Complexity:

        Best Case Sorted array as input, [ O(N) ]. And O(1) swaps.
        Worst Case: Reversely sorted, and when inner loop makes maximum comparison, [ O(N2) ] . And O(N2) swaps.
        Average Case: [ O(N2) ] . And O(N2) swaps.
        Space Complexity: [ auxiliary, O(1) ]. In-Place sort.

        Advantage:
        It can be easily computed.
        Best case complexity is of O(N) while the array is already sorted.
        Number of swaps reduced than bubble sort.
        For smaller values of N, insertion sort performs efficiently like other quadratic sorting algorithms.
        Stable sort.
        Adaptive: total number of steps is reduced for partially sorted array.
        In-Place sort.

        Disadvantage:
        It is generally used when the value of N is small. For larger values of N, it is inefficient.
         */
        fun insertionSort(array: IntArray): IntArray {

            for (i in 1 until array.size) {

                val key = array[i]

                /*
                 * Key is like the new card to be inserted to sorted deck
                 * if it's less than element j move it one index back until it's placed to correct position
                 * 19, 14, 16, 6, 4, 8, 12, 5, 9
                 * first key 14
                 * 19(14), 14(19), 16, 6, 4, 8, 12, 5, 9
                 *
                 * second key 16
                 * 14, 19, 16, 6, ...
                 * 14, 16, 19     <-
                 */

                var j = i - 1

                while (j >= 0 && key < array[j]) {
                    array[j + 1] = array[j]
                    j--
                }

                array[j + 1] = key

            }
            return array
        }


        // Merges two sub-arrays of array[].
        // First sub-array is array[left..middle]
        // Second sub-array is array[middle+1..right]
        private fun merge(array: IntArray, left: Int, middle: Int, right: Int) {
            // Find sizes of two sub-arrays to be merged
            val leftArraySize = middle - left + 1
            val rightArraySize = right - middle

            /* Create temp arrays */
            val LeftArray = IntArray(leftArraySize)
            val RightArray = IntArray(rightArraySize)

            /*Copy data to temp arrays*/
            for (i in 0 until leftArraySize)
                LeftArray[i] = array[left + i]

            for (j in 0 until rightArraySize)
                RightArray[j] = array[middle + 1 + j]


            /* Merge the temp arrays */

            // Initial indexes of first and second sub-arrays
            var leftIndex = 0
            var rightIndex = 0

            // Initial index of merged sub-array array
            var k = left
            while (leftIndex < leftArraySize && rightIndex < rightArraySize) {

                if (LeftArray[leftIndex] <= RightArray[rightIndex]) {
                    array[k] = LeftArray[leftIndex]
                    leftIndex++
                } else {
                    array[k] = RightArray[rightIndex]
                    rightIndex++
                }
                k++
            }

            /* Copy remaining elements of L[] if any */
            while (leftIndex < leftArraySize) {
                array[k] = LeftArray[leftIndex]
                leftIndex++
                k++
            }

            /* Copy remaining elements of R[] if any */
            while (rightIndex < rightArraySize) {
                array[k] = RightArray[rightIndex]
                rightIndex++
                k++
            }
        }

        // Main function that sorts array[left..right] using
        // merge()
        fun mergeSort(array: IntArray, left: Int, right: Int) {
            if (left < right) {
                // Find the middle point
                val m = (left + right) / 2

                // Sort first and second halves
                mergeSort(array, left, m)
                mergeSort(array, m + 1, right)

                // Merge the sorted halves
                merge(array, left, m, right)
            }
        }

        fun quickSort(array: IntArray, low: Int, high: Int) {

            if (low < high) {

                /* pi is partitioning index, array[pi] is
                  now at right place */
                val pi = partition(array, low, high)

                // Sort items before partition index
                quickSort(array, low, pi - 1)

                // Sort items after partition index
                quickSort(array, pi + 1, high)

            }

        }

        private fun partition(array: IntArray, low: Int, high: Int): Int {

            var i = low - 1

            val pivot = array[high]

            //
            for (j in low until high) {

                if (array[j] < pivot) {

                    i++

                    // rearrange the array by putting elements which are less than pivot
                    // on one side and which are greater that on other.
                    val temp = array[i]
                    array[i] = array[j]
                    array[j] = temp

                }
            }

            // swap arr[i+1] and arr[high] (or pivot)
            val temp = array[i + 1]
            array[i + 1] = array[high]
            array[high] = temp
            // Initial: 19, 14, 16, 6, 4, 8, 12, 5, 9, 17

            return i + 1

        }


        // Prints the Array on the screen in a grid


        private fun printArrayFormatted(array: IntArray) {

            println("----------")
            for (i in 0 until array.size) {
                print("| $i | ")
                println("${array[i]}  |")
                println("----------")
            }
        }

        private fun printHorizontalArray(array: IntArray, i: Int, j: Int) {

            val arraySize = array.size

            for (n in 0..50) print("-")

            println()

            for (n in 0 until arraySize) {

                print("| $n  ")

            }

            println("|")

            for (n in 0..50) print("-")

            println()

            for (n in 0 until arraySize) {

                print("| " + array[n] + " ")

            }

            println("|")

            for (n in 0..50) print("-")

            println()

            // END OF FIRST PART


            // ADDED FOR BUBBLE SORT

            if (j != -1) {
                // ADD THE +2 TO FIX SPACING
                for (k in 0 until j * 5 + 2) print(" ")
                print(j)

            }


            // THEN ADD THIS CODE

            if (i != -1) {
                // ADD THE -1 TO FIX SPACING
                for (l in 0 until 5 * (i - j) - 1) print(" ")
                print(i)
            }

            println()

        }


        fun printArray(array: IntArray): String {
            val stringBuilder = StringBuilder()

            stringBuilder.append("{")

            array.forEachIndexed() { index, value ->
                if (index == array.size - 1) {
                    stringBuilder.append("$value}")
                } else {
                    stringBuilder.append("$value, ")
                }
            }

            return stringBuilder.toString()

        }


        fun generateRandomArray(arraySize: Int): IntArray {

            val array = IntArray(arraySize)
            for (i in 0 until arraySize) {
                // Random number 10 through 19
                array[i] = (Math.random() * 10).toInt() + 10

            }
            return array
        }
    }

}