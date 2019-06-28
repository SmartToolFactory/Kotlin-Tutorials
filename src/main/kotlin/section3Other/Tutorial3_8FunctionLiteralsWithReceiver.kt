package section3Other

fun main(args: Array<String>) {

    // INFO 🔥 Function Literal with Receiver

    // Function Literal with Receiver
    createString({

    })

    // Function Literal with Receiver without Paranthesis
    val s = createString {
        //here we're in the context of a `StringBuilder`
        append(4)
        append("hello")
    }
    println(s)


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


// INFO 🔥 Function Literal with Receiver
// StringBuilder.() sets this function as extension function of StringBuilder class
//  receiver is defined as StringBuilder
inline fun createString(block: StringBuilder.() -> Unit): String {

    val sb = StringBuilder()     // create the receiver object
    sb.block()                   // pass the receiver object to the lambda

    return sb.toString()
}

inline fun StringBuilder.extra(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

inline fun StringBuilder.extra2(value: Int, block: (Int) -> Int): StringBuilder {
    block(value)
    return this
}
