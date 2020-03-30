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

    // INFO 🔥 Lambdas

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
    compLambda.invoke(2, 4)

    // Lambda
    val comp2Lambda = { a: Int, b: Int ->
        a == b
    }
    // Invocation of lambda
    val res2Lambda: Boolean = comp2Lambda(2, 4)

    // INFO 🔥 Lambda Functions
    // 🔥 myFun is a function that is (String) -> String = { str -> str.reversed() }
    val myFun = bar()
    println(myFun("Hello world"))


    val isEven = modulo(2)
    // 🔥 k = 2, 'it' = 3, and isItEven = (Int) -> Boolean = { it % k == 0 }
    val isItEven = isEven(3)
    val myList = arrayListOf(1, 2, 3)
    val result = myList.filter(isEven)


    val myFunction = lambdaFunctionWithParam(13)
    val test = myFunction()
    println("Test String Concatenation $test")


    val totalSum = highOrderSum(3, { 4 })
    println("totalSum: $totalSum")

    val totalSum2 = highOrderSum2(3) {
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

    // Both implementation works
    // Alternative 1
    val lambdaFuncVar = concatLambdaFunction(13)
    val lambdaFuncResult = lambdaFuncVar("Try this")
    println(lambdaFuncResult)

    // Alternative 2
    val lambdaFuncResult2 = concatLambdaFunction(12)("Test")
    println(lambdaFuncResult2)

    // INFO 🔥 High-order functions
    // Alternative 1 to pass function to high order functions
    highOrderFunction(3) {
        parameterFunction(it)
    }

    // Alternative 2 to pass function to high order functions
    highOrderFunction(3, ::parameterFunction)

    "Hello World".highTes {
        it.reversed()
    }


}

fun String.highTes(action: (String) -> String) {
    action(this)
}

// INFO 🔥 Lambda Functions

fun bar(): (String) -> String = { str -> str.reversed() }

fun modulo(k: Int): (Int) -> Boolean = { it % k == 0 }

/**
 * Lambda function that takes num Int as parameter and returns a function that takes
 * a String as parameter and returns a String
 */
fun concatLambdaFunction(num: Int): (String) -> String = {
    "$num + $it"
}

/**
 * Lambda function that takes args and returns a lambda
 */
fun lambdaFunctionWithParam(arg: Int): () -> Int = {
    val message = "I was created by CreateFunction()"
    println("createFunction() message: $message")
    arg * 2
}

// INFO 🔥 High-order functions
private fun highOrderSum(a: Int, predicate: () -> Int): Int {
    return a + predicate()
}

private fun highOrderSum2(a: Int, predicate: (Int) -> Int): Int {
    return a + predicate(a)
}

fun highOrderFunction(num: Int, action: (Int) -> Boolean): Boolean {
    return action(num)
}

fun parameterFunction(num: Int): Boolean {
    return num % 2 == 0
}