package com.smarttoolfactory.tutorial

fun main() {
    var arr = intArrayOf(-1,-3)
//    var arr = intArrayOf(1, 2, 3)
//var arr = intArrayOf(1, 3, 6, 4, 1, 2)

    val res = solution(arr)
    println("RESULT: $res")
}

// you can write to stdout for debugging purposes, e.g.
// println("this is a debug message")
fun solution(arr: IntArray): Int {


    val set = HashSet<Int>()
    for (i in arr.indices) {
        if (!set.contains(arr[i])) {
            set.add(arr[i])
        }
    }

    for (i in 1..arr.size+1) {
        println("i: $i")
        if (!set.contains(i)) {
            return i
        }
    }

    return 1
}