package chapter1Basics

fun main(args: Array<String>) {

    // INFO Default Argument Functions
    println(myFunction("Earth"))
    println(myFunction2())

    foo(1) { println("hello") } // Uses the default value baz = 1
    foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
    foo { println("hello") } // Uses both default values bar = 0 and baz = 1

    // INFO Named Function
    namedFunc(number = 4)

    // INFO Single Expression Function

    // INFO Vararg Function
    val myList = asList("1", "2", "3")
    myList.forEach { it -> println("List $it") }

    // INFO Infix Notation Function
    infix fun Int.shl(x: Int): Int {
        return x * 2;
    }

// calling the function using the infix notation
    1 shl 2
// is the same as
    1.shl(2)

    infix fun String.sameAs(x: String): Boolean {
        return this == x
    }

  val isThisTrue =  "Obj" sameAs "Obj"

    println("🎃 Infix result $isThisTrue")

    // INFO Tail Recursive Function

    println("Factorial result: ${fact(5)}")

}

/**
 * myFunction has x as parameter and returns a String defined with : String as return type
 */
fun myFunction(x: String): String {
    val c: String = "Hey!! Welcome To ---"
    return (c + x)
}

// INFO Default Arguments
/**
 * Function parameters can have default values, which are used when a corresponding argument is omitted.
 * This allows for a reduced number of overloads compared to other languages:
 */
fun myFunction2(x: String = "Universe"): String {
    val c: String = "Hey!! Welcome To ---"
    return (c + x)
}

fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {
    // Do some stuff...
}

// bar = 0, and baz = 1 are the default values.
fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
    println("foo() bar: $bar, baz: $baz")
}

// INFO Named Arguments
fun namedFunc(number: Int = 5, text: String = "Empty") {
    println("Number $number, text: $text")
}

// INFO Single Expression
fun double(x: Int): Int = x * 2

// Explicitly declaring the return type is optional when this can be inferred by the compiler:
fun double2(x: Int) = x * 2

// INFO Vargarg Arguments
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}


// INFO Tail Recursive

// Recursive function that is optimized with tailRec keyword
fun fact(x: Int): Int {

    tailrec fun factTail(y: Int, z: Int): Int {
        println("FactTail x: $x, y: $y, z: $z")
        return if (y == 1) z else factTail(y - 1, y * z)
    }

    return factTail(x, 1)

}

