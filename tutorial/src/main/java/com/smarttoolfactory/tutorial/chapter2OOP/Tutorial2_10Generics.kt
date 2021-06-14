package com.smarttoolfactory.tutorial.chapter2OOP

fun main() {
    val box = Box(1) // 1 has type Int, so the compiler figures out that we are talking about Box<Int>


    test(12)
    test(12.4f)
}


class Box<T>(t: T) {
    var value = t
}

fun <T: Number> test(num: T) {

}

