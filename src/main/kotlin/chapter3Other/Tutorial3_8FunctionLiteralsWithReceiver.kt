package chapter3Other

fun main() {


    // INFO 🔥 High Order Function

    val test = createString(5) {
        println("createTest() $it")
        it
    }

    val test2 = createString(4, {
        it / 2
    })

    print("Test2 $test2")


    // INFO 🔥 Function Literal with Receiver

    // Function Literal with Receiver
    createStringWithLiteral({

    })

    // Function Literal with Receiver without Parenthesis
    val s = createStringWithLiteral {

        //here we're in the context of a `StringBuilder`
        append(4)
        append("hello")
        // functions inside {} are basically call to sb.block() in function body
    }
    println(s)


    html {
        this.body()
    }


    // INFO 🔥 Extension Function Literal with Receiver
    val sb = StringBuilder()
    val sbNew = sb.extra({

    })

    sb.extra {

    }

    val ch = sbNew.extra2(3) {
        val num = it * 2
        num
    }


}

// INFO 🔥 High Order Function
inline fun createString(value: Int, block: (Int) -> Int): String {
    return block(value * value).toString()
}

// INFO 🔥 Function Literal with Receiver
// StringBuilder.() sets this function as extension function of StringBuilder class
//  receiver is defined as StringBuilder
inline fun createStringWithLiteral(block: StringBuilder.() -> Unit): String {

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
inline fun StringBuilder.extra(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

inline fun StringBuilder.extra2(value: Int, block: (Int) -> Int): StringBuilder {
    block(value)
    return this
}
