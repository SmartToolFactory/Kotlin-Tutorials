package com.smarttoolfactory.tutorial.chapter4Functions

fun main() {


    // INFO 🔥 Custom class that implements a function type as an interface
    val intFunction: (Int) -> Int = IntTransformer()
    println("intFunction: $intFunction(5)") // prints x * x


    // Lambda Expression that takes 2 int params and returns int
    val testFun: (Int, Int) -> Int = { x, y -> x + y }
    val testFunInferred = { x: Int, y: Int -> x + y }
    // Lambda means-> testFun: (Int, Int) -> Int
    val resFun = testFun(2, 4)

//    The compiler can infer the function types for variables if there is enough information:
    val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
    println("a ${a(2)}")


    // INFO 🔥🔥 BaseClassA value of type (BaseClassA, InterfaceB) -> C can be passed or assigned
    // where a BaseClassA.(InterfaceB) -> C
    val repeatFun: String.(Int) -> String = { times:Int ->
        this.repeat(times)
    }
    val twoParameters: (String, Int) -> String = repeatFun // OK
    val twoParams: (String, Int) -> String = { str, times -> str.repeat(times) }

    val result = runTransformation(repeatFun) // OK
    val result2 = runTransformation(twoParameters)
    val result3 = runTransformation(twoParams)

    twoParameters("hi", 4)

    println("result: $result") // prints hello-hello-hello-
    println("result2: $result2") // prints hello-hello-hello-
    println("result3: $result3") // prints hello-hello-hello-


    // INFO 🔥 Invoking a function type instance

//    If the value has a receiver type, the receiver object should be passed as the first argument.
//    Another way to invoke a value of a function type with receiver is to prepend it with the receiver object,
//    as if the value were an extension function: 1.foo(2) ,

    val stringPlus: (String, String) -> String = String::plus

    // INFO 🔥 Both mean same thing
//    val intPlus: Int.(Int) -> Int = Int::plus
    val intPlus: Int.(Int) -> Int = { x -> this.plus(x) }

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // extension-like call


    val testString = "Hello World"

    // INFO High Order Function
    val resultHighOrder: String = exampleHighOrder(testString) {
        it.toUpperCase()
    }

    // INFO Function Literal With Receiver
    val resultLiteralReceiver: String = exampleLiteralWithReceiver(testString) {
        toUpperCase()
    }

    // INFO Extension Function
    val resultExtension: String = testString.exampleExtension {
        it.toUpperCase()
    }

    // INFO Extension Function that Literal With Receiver
    val resultExtensionLiteral: String = testString.exampleLiteralExtension {
        toUpperCase()
    }

    println("TEST-> resultHighOrder: $resultHighOrder, resultLiteralReceiver: $resultLiteralReceiver, resultExtension: $resultExtension, resultExtensionLiteral: $resultExtensionLiteral")


}


// INFO 🔥 Custom class that implements a function type as an interface
class IntTransformer : (Int) -> Int {
    //    override operator fun invoke(x: Int): Int = TODO()
    override operator fun invoke(x: Int): Int = x * x
}

// INFO 🔥 BaseClassA value of type (BaseClassA, InterfaceB) -> C can be passed or assigned where a BaseClassA.(InterfaceB) -> C
fun runTransformation(action: (String, Int) -> String): String {
    return action("hello-", 3)
}

/*
 INFO 🔥 Both functions give the same result
  * First function is High Order function that takes function as a param that takes String as param
  * Second function is Function Literal With Receiver that is action is extension function of String
  * Third one is Extension Function which is called by a String object only
  * and this corresponds to String inside the function
 */

// INFO 🔥 High Order Function
fun exampleHighOrder(value: String, action: (String) -> String): String {
    return action(value)
}

/**
action: String.() defines here that if we have  a String inside this function
that String call action() with no params since its String extension with .()

😎 Basically it means: action() lambda will be called only by receiver(String) inside this high-order
function.

🎃 If we want to call lambda action() with any param, for instance action(String)
we should change action: String.() to String.(String)

 */
// INFO 🔥 Function Literal With Receiver
fun exampleLiteralWithReceiver(value: String, action: String.() -> String): String {
    // INFO Both result implementations are the same

    // value here act as String of String.() and action is without params()
    val result = value.action()
    return result
}

/**
 * This is an extension function that does not require a String param since only String class instance
 * can call this function, and this caller String can be sent as a parameter to lambda
 * by using action(this)
 */
// INFO 🔥 High Order Extension Function
fun String.exampleExtension(action: (String) -> String): String {
    val result = action(this)
    return result
}

/**
 * This function is an extension function which can be called only by a String instance.
 * Since it has String.() literal with receiver and receiver is a String,
 * it's already called on a String which does not require a parameter as the function above
 */
// INFO 🔥 Extension Function that Literal With Receiver
fun String.exampleLiteralExtension(action: String.() -> String): String {
    // INFO Both result implementations are the same
    val result = action()
    return result
}

