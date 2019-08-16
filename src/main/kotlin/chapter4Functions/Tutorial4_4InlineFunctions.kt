package chapter4Functions

fun main(args: Array<String>) {

    // INFO 🔥 Inline Function

    // Creates instance of lambda function instance every time invoked
    nonInlined {
        println("Hello from NON-inlined")
    }

    // only gets block inside lambda, does not create instance
    inlined {
        println("Hello from inlined")
    }

}


fun nonInlined(block: () -> Unit) {
    println("before")
    block()
    println("after")
}


inline fun inlined(block: () -> Unit) {
    println("before")
    block()
    println("after")
}

