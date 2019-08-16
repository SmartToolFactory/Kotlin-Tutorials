package chapter4Functions

fun main() {


    // INFO 🔥 Custom class that implements a function type as an interface
    val intFunction: (Int) -> Int = IntTransformer()
    println("intFunction: $intFunction(5)") // prints x * x

    // Function that takes 2 int params and returns int
    val testFun: (Int, Int) -> Int = { x, y -> x + y }
    val testFunInferred = { x: Int, y: Int -> x + y }

//    The compiler can infer the function types for variables if there is enough information:
    val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
    println("a ${a(2)}")


    // INFO 🔥 A value of type (A, B) -> C can be passed or assigned where a A.(B) -> C
    val repeatFun: String.(Int) -> String = { times ->
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
    val resultHighOrder = testHighOrder(testString) {
        it.toUpperCase()
    }

    // INFO Function Literal With Receşver
    val resultLiteralReceiver = testLiteralWithReceiver(testString) {
        toUpperCase()
    }

    // INFO Extension Function
    val resultExtension = testString.testExtension {
        it.toUpperCase()
    }

    // INFO Extension Function that Literal With Receiver
    val resultExtensionLiteral = testString.testLiteralExtension {
        toUpperCase()
    }

    println("TEST-> resultHighOrder: $resultHighOrder, resultLiteralReceiver: $resultLiteralReceiver, resultExtensionLiteral: $resultExtensionLiteral")


}


// INFO 🔥 Custom class that implements a function type as an interface
class IntTransformer : (Int) -> Int {
    //    override operator fun invoke(x: Int): Int = TODO()
    override operator fun invoke(x: Int): Int = x * x
}

// INFO 🔥 A value of type (A, B) -> C can be passed or assigned where a A.(B) -> C
fun runTransformation(action: (String, Int) -> String): String {
    return action("hello-", 3)
}

/*
 INFO 🔥 Both functions give the same result
  * First function is High Order function that takes function as a param that takes String as param
  * Second function is Function Literal With Receiver that is action is extension function of String
 */

// INFO High Order Function
fun testHighOrder(value: String, action: (String) -> String): String {
    return action(value)
}

// INFO Function Literal With Receiver
fun testLiteralWithReceiver(value: String, action: String.() -> String): String {
    // INFO Both result implementations are the same
//    val result =  action(value)
    val result = value.action()
    return result
}

// INFO Extension Function
fun String.testExtension(action: (String) -> String) {
    action(this)
}

// INFO Extension Function that Literal With Receiver
fun String.testLiteralExtension(action: String.() -> String): String {
    // INFO Both result implementations are the same
    val result = action()
    return result
}


