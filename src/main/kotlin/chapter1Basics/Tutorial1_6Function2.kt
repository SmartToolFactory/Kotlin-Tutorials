package chapter1Basics

/*
    Lambda expression takes parameters from when it's invoked
    definition uses that argument in codeBody

    val lambdaName : Type = { argumentList -> codeBody }
    or
    val lambdaName: Type = {argument: TypeOfArgument -> codeBody }

    *** Instantiating a function type  ***
    There are several ways to obtain an instance of a function type:

    Using a code block within a function literal, in one of the forms:
    a lambda expression: { a, b -> a + b },

    an anonymous function: fun(s: String): Int { return s.toIntOrNull() ?: 0 }
    Function literals with receiver can be used as values of function types with receiver.

    Using a callable reference to an existing declaration:
    a top-level, local, member, or extension function: ::isOdd, String::toInt,

    a top-level, member, or extension property: List<Int>::size,
    a constructor: ::Regex
    These include bound callable references that point to a member of a particular instance: foo::toString.
 */
fun main() {

    // Lambda
    val myLambda: (String) -> Unit = { s -> println(s) }
    val v: String = "Tutorials"
    // Invocation of lambda
    myLambda(v)

    // Lambda
    val compLambda: (Int, Int) -> Boolean = { a, b ->
        a == b
    }
    // Invocation of lambda
    val resLambda = compLambda(2, 4)

    // Lambda
    val comp2Lambda = { a: Int, b: Int ->
        a == b
    }
    // Invocation of lambda
    val res2Lambda = comp2Lambda(2, 4)

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


    // Lambda with String parameter that returns a String
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

    val resList = swapLambda(0, 1, listNumber)

    println("Result List: $resList")


    val res = concat(13)
    val r = res("Try this")
    println(r)

    val re = concat(12)("Test")
    println(re)


    test(3) {
        testBoolean(it)
    }

    test(3, ::testBoolean)

}

fun testBoolean(num: Int): Boolean {
    return num % 2 == 0
}

fun test(num: Int, action: (Int) -> Boolean): Boolean {
    return action(num)
}

/*
   *******  Lambda Functions *******
 */

fun bar(): (String) -> String = { str -> str.reversed() }

fun modulo(k: Int): (Int) -> Boolean = { it % k == 0 }


/**
 * Lambda function that takes num Int as parameter and returns a function that takes
 * a String as parameter and returns a String
 */
fun concat(num: Int): (String) -> String = {
    "$num + $it"
}

/**
 * Lambda function that takes args and returns a lambda
 */
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





