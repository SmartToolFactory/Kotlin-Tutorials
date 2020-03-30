package chapter3Other

fun main() {


    // INFO 🔥 High Order Function

    val test = createString(5, {
        println("createTest() $it")
        it * 2
    })

    println("test: $test")

    val test2 = createString(4, {
        it / 2
    })

    println("Test2 $test2")


    // {} = block: (StringBuilder) -> Unit
    val stringFromHighOrder = createStringFromStringBuilder({
        it.append(4)
        it.append("Hello")
    })


    val stringFromHighOrder2 = createStringFromStringBuilder(lambdaString("Hello", "World"))
    println("stringFromHighOrder2: $stringFromHighOrder2")

    println("stringFromHighOrder $stringFromHighOrder")


    // INFO 🔥 Function Literal with Receiver

    // Function Literal with Receiver
    val stringFromLiteralWithReceiver = createStringWithLiteral({
        append(4)
        append("hello")
    })


    // Function Literal with Receiver without Parenthesis
    val stringFromLiteralWithReceiver2 = createStringWithLiteral {

        //here we're in the context of a `StringBuilder`
        append(4)
        append("hello")
        // functions inside {} are basically call to sb.block() in function body
    }
    println("stringFromLiteralWithReceiver2: $stringFromLiteralWithReceiver2")
//
//
//    html {
//        this.body()
//    }
//
//
//    // INFO 🔥 Extension Function Literal with Receiver
//    val sb = StringBuilder()
//    val sbNew = sb.extra({
//
//    })
//
//    sb.extra {
//
//    }
//
//    val ch = sbNew.extra2(3) {
//        val num = it * 2
//        num
//    }


}

// INFO 🔥 High Order Functions
fun createString(value: Int, block: (Int) -> Int): String {
    return block(value * value).toString()
}

fun createStringFromStringBuilder(block: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    block(sb) // 🔥 This CAN NOT be sb.block()
    return sb.toString()
}

// Lambda function to pass as parameter to high-order function
fun lambdaString(vararg texts: String): (StringBuilder) -> Unit = {
    texts.forEach { text ->
        it.append(text)
    }


}

// INFO 🔥 Function Literal with Receiver
// StringBuilder.() sets this function as extension function of StringBuilder class
//  receiver is defined as StringBuilder
fun createStringWithLiteral(block: StringBuilder.() -> Unit): String {

    val sb = StringBuilder()     // create the receiver object
    sb.block()                   // 🔥 This can also be  block(sb)

    // pass the receiver object to the lambda

    return sb.toString()
}

class HTML {
    fun body() {
        println("This is the body of HTML file")
    }
}

fun html(init: HTML.() -> Unit): HTML {

    val html = HTML() // create the receiver object
    html.init() // pass the receiver object to the lambda return html
    return html
}


// INFO 🔥 Extension Function Literal with Receiver
/**
 * This function is extension function of [StringBuilder] class by [StringBuilder]. before function name.
 *
 * It takes a block parameter which is a function literal receiver which returns StringBuilder instance
 * in function implementation
 */
fun StringBuilder.extra(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

fun StringBuilder.extra2(value: Int, block: (Int) -> Int): StringBuilder {
    block(value)
    return this
}


