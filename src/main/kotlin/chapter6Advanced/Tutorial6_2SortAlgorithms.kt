package chapter6Advanced

import chapter6Advanced.SortUtils.Companion.bubbleSort
import chapter6Advanced.SortUtils.Companion.printArray
import chapter6Advanced.SortUtils.Companion.selectionSort
import java.lang.StringBuilder

/*
    Algorithm	Time Complexity
                        Best	        Average 	    Worst
    Selection Sort	    Ω(n^2)	        θ(n^2)	        O(n^2)
    Bubble Sort	        Ω(n)	        θ(n^2)	        O(n^2)
    Insertion Sort	    Ω(n)	        θ(n^2)	        O(n^2)
    Heap Sort	        Ω(n log(n))	    θ(n log(n))	    O(n log(n))
    Quick Sort	        Ω(n log(n))	    θ(n log(n))	    O(n^2)
    Merge Sort	        Ω(n log(n))	    θ(n log(n))	    O(n log(n))
    Bucket Sort     	Ω(n+k)	        θ(n+k)	        O(n^2)
    Radix Sort	        Ω(nk)	        θ(nk)	        O(nk)
 */

fun main() {

    val unsortedArray = intArrayOf(6, 7, 4, 8, 12, 5, 9)

    // Bubble Sort
    val bubbleSortedArray = bubbleSort(unsortedArray)
    println("bubbleSortedArray: ${printArray(bubbleSortedArray)}")

    val selectionSortedArray = selectionSort(unsortedArray)
    println("selectionSortedArray: ${printArray(selectionSortedArray)}")

}


class SortUtils {

    companion object {

        // {6, 7, 4, 8, 12, 5, 9}

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
                }

            }

            return array

        }

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
                    if (array[j] < array[min]) {
                        min = j
                    }
                }

                val temp = array[min]
                array[min] = array[i]
                array[i] = temp

            }

            return array

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

    }

}