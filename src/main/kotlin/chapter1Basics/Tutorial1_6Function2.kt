package chapter1Basics


fun main() {

    val myLambda: (String) -> Unit = { s -> println(s) }
    val v: String = "Tutorials"
    myLambda(v)

    // 🔥 myFun is a function that is (String) -> String = { str -> str.reversed() }
    val myFun = bar()
    println(myFun("Hello world"))


    val isEven = modulo(2)
    // 🔥 k = 2, 'it' = 3, and isItEven = (Int) -> Boolean = { it % k == 0 }
    val isItEven = isEven(3)
    val myList = arrayListOf(1, 2, 3)
    val result = myList.filter(isEven)


    val myFunction = createFunction(13)
    val test = myFunction()
    println("Test String Concatenation $test")


    val totalSum = sum(3, { 4 })
    println("totalSum: $totalSum")

    // 🔥
    val totalSum2 = sum2(3) {
        it * 2
    }
    println("totalSum2: $totalSum2")


    val filter: (Int) -> Boolean = { it > 2 }
    // block is {it * 2}
    val myOps = customOperation(4, {
        it * 2
    })
    println(myOps)

    val anotherLambda: (String) -> String = { s -> s.toUpperCase() }
    val lambdaResult = anotherLambda("Hello World")
    println(lambdaResult)


    val swapLambda: (Int, Int, MutableList<Int>) -> List<Int> = { index1, index2, list ->

        require(!(index1 < 0 || index2 < 0)) { "Index cannot be smaller than zero" }

        require(!(index1 > list.size - 1 || index2 > list.size - 1)) { "Index cannot be bigger than size of the list" }

        val temp = list[index1]

        list[index1] = list[index2]
        list[index2] = temp

        list
    }

    val listNumber = mutableListOf<Int>(1, 2, 3)

   val resList = swapLambda(0,1, listNumber)

    println("Result List: $resList")



    val res = concat(13)
    val r = res("Try this")
    println(r)

    val re = concat(12)("Test")
    println(re)


}

/*
    Lambda Functions
 */
fun bar(): (String) -> String = { str -> str.reversed() }

fun modulo(k: Int): (Int) -> Boolean = { it % k == 0 }

fun createFunction(arg: Int): () -> Int {
    val message = "I was created by CreateFunction()"
    println("createFunction() message: $message")
    return { arg }
}

/*
    High Order Functions
 */
private fun sum(a: Int, testFun: () -> Int): Int {
    return a + testFun()
}

private fun sum2(a: Int, testFun: (Int) -> Int): Int {
    return a + testFun(a)
}

/**
 * This function takes block function as parameter and sends a to block function and returns
 * String after executing block function
 */
private fun customOperation(a: Int, block: (Int) -> Int): String {
    return "result of customOperation: ${block(a)}"
}


/**
 * Lambda function that takes num Int as paremeter and returns a function that takes
 * a String as parameter and returns a String
 */
fun concat(num: Int): (String) -> String = {
    "$num + $it"
}



