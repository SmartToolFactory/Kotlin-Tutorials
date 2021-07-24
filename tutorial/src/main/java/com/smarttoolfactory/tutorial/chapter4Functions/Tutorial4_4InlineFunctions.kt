package com.smarttoolfactory.tutorial.chapter4Functions

fun main() {

    // INFO 🔥 Inline Function
    /**********************************************/
    // Creates instance of lambda function instance every time invoked
    nonInlined {
        println("Hello from NON-inlined")
    }

    // only gets block inside lambda, does not create instance
    inlined {
        println("Hello from inlined")
    }
    /**********************************************/

    val list = arrayListOf<Int>()
    for (number in 1..10) {
        list.add(number)
    }

    // 🔥 'it' in lambda is every element that this list contains iterated one by one
    val resultList = list.filterOnCondition { isMultipleOf(it, 5) }
    println("filterOnCondition resultList : $resultList")


    // 🔥 'this' in lambda is every element that this list contains iterated one by one
    val resultListWithReceiver = list.filterWithCondition {
        isMultipleOf(this, 5)
    }
    println("filterWithCondition resultListWithReceiver : $resultListWithReceiver")

    /**********************************************/
}


fun nonInlined(block: () -> Unit) {
    println("before")
    block()
    println("after")
}


inline fun inlined(block: () -> Unit) {
    println("before")
    block()
    println("after")
}

/*
* Gives the following warning because the inline function does not take any Function object
* Expected performance impact from inlining is insignificant. Inlining works best for functions with parameters of functional types
* */
inline fun isMultipleOf(number: Int, multipleOf: Int): Boolean {
    return number % multipleOf == 0
}

fun <T> ArrayList<T>.filterOnCondition(condition: (T) -> Boolean): ArrayList<T> {
    val result = arrayListOf<T>()
    for (item in this) {
        if (condition(item)) {
            result.add(item)
        }
    }
    return result
}

fun <T> ArrayList<T>.filterWithCondition(condition: T.() -> Boolean): ArrayList<T> {
    val result = arrayListOf<T>()

    for (item in this) {

        /*
            🔥 our parameter is literal with Receiver here, T.() -> Boolean
            so we can call condition() function on T
         */

        if (item.condition()) {
            result.add(item)
        }
    }

    return result
}