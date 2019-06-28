package section4Functions

fun main(args: Array<String>) {


    // INFO 🔥 Custom class that implements a function type as an interface
    val intFunction: (Int) -> Int = IntTransformer()
    println("intFunction: $intFunction(5)") // prints x * x

//    The compiler can infer the function types for variables if there is enough information:
    val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
    println("a ${a(2)}")


    // INFO 🔥 A value of type (A, B) -> C can be passed or assigned where a A.(B) -> C
    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
    val twoParameters: (String, Int) -> String = repeatFun // OK

    val result = runTransformation(repeatFun) // OK
    val result2 = runTransformation(twoParameters)
    twoParameters("hi", 4)

    println("result: $result") // prints hello-hello-hello-
    println("result2: $result2") // prints hello-hello-hello-


    // INFO 🔥 Invoking a function type instance

//    If the value has a receiver type, the receiver object should be passed as the first argument.
//    Another way to invoke a value of a function type with receiver is to prepend it with the receiver object,
//    as if the value were an extension function: 1.foo(2) ,

    val stringPlus: (String, String) -> String = String::plus

    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // extension-like call
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
