package chapter4Functions


/*
    Lambda function takes parameters from when it's invoked
    definition uses that argument in codeBody

    val lambdaName : Type = { argumentList -> codeBody }
    or
    val lambdaName: Type = {argument: TypeOfArgument -> codeBody }
 */
fun main() {


    // INFO 🔥 Lambda Expression
    val test: (String) -> Unit = { s -> println(s) }
    test("Test String")
    val test2 = { s: String -> println(s) }
    test2("Test String 2")

    val sumAlternative1 = { x: Int, y: Int -> x + y }
    val sumAlternative2: (Int, Int) -> Int = { x, y -> x + y }
    println("Sum Alt1: ${sumAlternative1(2, 3)}")
    println("Sum Alt2: ${sumAlternative2(2, 3)}")

    // INFO Passing Lambda as last parameter
    val items = listOf<Int>(1, 2, 3)

    // If lambda is the last parameter it can be taken outside the parenthesis
    val prod = items.fold(1, { acc, e -> acc * e })
    val product = items.fold(1) { acc, e -> acc * e }

    // If the lambda is the only argument to that call, the parentheses can be omitted entirely:
    run { println("...") }

    // Info Single parameter it
    val ints = listOf<Int>(1, 2, 3)
    ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'


    // INFO 🔥🔥 Lambda Expression with Receiver

    // Lambda expression  that has a Receiver String. and function literal () -> Unit that returns Unit

    val greet: String.() -> Unit = {
        println("Function Literal Receiver: $this")
    }

    // Both implementations have same results
    greet("TestStringConcatenation String")
    "TestStringConcatenation String".greet()

    // Lambda expression that has a Receiver String. and function literal () -> String that returns a String
    val myString: String.() -> String = {
        "length is ${this.length}"
    }
    println("myString: ${myString("TestStringConcatenation")}")

    // Lambda expression that has a Receiver Int. and function literal () -> Boolean that returns a Boolean
    val isEven: Int.() -> Boolean = { this % 2 == 0 }
    isEven(3)


    val total: Int.(Int) -> Int = { other -> plus(other) }

    // INFO 🔥🔥 Anonymous Function with Receiver

//    The anonymous function syntax allows you to specify the receiver type of a function literal directly.
//    This can be useful if you need to declare a variable of a function type with receiver, and to use it later.
    val total2 = fun Int.(other: Int): Int = this + other

    println("Function Literal with Receiver total2: Int.(Int)-> Int: " + total(3, 4))


    // INFO 🔥🔥 Function Literal with Receiver

    // This function is defined as Function Literal with Receiver
    val isOdd = isOdd(4) {
        this % 2 == 1
    }

    createString({

    })

    // Function Literal with Receiver without Parentheses
    val stringCreated = createString {
        //here we're in the context of a `StringBuilder`
        append(4)
        append("hello")


    }
    println("stringCreated $stringCreated")


    val sb = StringBuilder()
    val sbNew = sb.extra({

    })

    sb.extra {

    }

    val ch = sbNew.extra2(3) {
        val num = it * 2
        num
    }

// INFO 🔥🔥 Function Literal that returns String
    val upperCase = createStringBlock(4) {

        // Thi predicate function returns String
        "createStringBlock $it"
    }

    println("Uppercase String: $upperCase")

}


// INFO 🔥🔥 Function Literal  that returns Boolean
fun isOdd(value: Int, action: Int.() -> Boolean): Boolean {
//    return action(value)
    return value.action()
}

// INFO 🔥🔥 High Oder Function
fun createStringBlock(num: Int, block: (Int) -> String): String {
    return block(num).toUpperCase()
}


// INFO 🔥🔥 Function Literal with Receiver
// StringBuilder.() sets this function as extension function of StringBuilder class
//  receiver is defined as StringBuilder
fun createString(block: StringBuilder.() -> Unit): String {

    val sb = StringBuilder()     // create the receiver object
    sb.block()                   // pass the receiver object to the lambda

    return sb.toString()
}

// INFO 🔥 Extension Functions Literal with Receiver
fun StringBuilder.extra(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

fun StringBuilder.extra2(value: Int, block: (Int) -> Int): StringBuilder {
    this.append(block(value).toString())
    return this
}




