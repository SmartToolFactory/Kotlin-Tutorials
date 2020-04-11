package chapter4Functions

fun main(args: Array<String>) {

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


    /**********************************************/
    var list = arrayListOf<Int>()
    for (number in 1..10){
        list.add(number)
    }
    val resultList = list.filterOnCondition { isMultipleOf(it, 5) }
    print("filterOnCondition resultList : " + resultList)
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
inline fun isMultipleOf (number: Int, multipleOf : Int): Boolean {
    return number % multipleOf == 0
}

fun <T> ArrayList<T>.filterOnCondition(condition: (T) -> Boolean): ArrayList<T>{
    val result = arrayListOf<T>()
    for (item in this){
        if (condition(item)){
            result.add(item)
        }
    }
    return result
}

