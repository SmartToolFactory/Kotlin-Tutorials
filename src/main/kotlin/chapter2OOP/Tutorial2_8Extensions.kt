package chapter2OOP

fun main(args: Array<String>) {

    // INFO 🔥 Extension function
    val testString: String = "Hello World  "
    println("Test ${testString.upperCaseAndTrim()}")

    val list = mutableListOf<Int>();
    list.add(1)
    list.add(2)
    list.add(3)

    list.swap(0, 1)
    list.forEach { it -> println("it $it") }

    // INFO 🔥 Extensions are resolved statically
    printFoo(D())
    // calls member function
    Cex().foo()

    // INFO 🔥 Overloaded extension function will call suitable overloaded function
    CExtension().foo(1) // prints extension

    // INFO 🔥 Companion Object Extensions
    MyClass.foo()

    // INFO 🔥 Declaring Extensions as Members
    val f = F()
    f.caller(G())


}

// INFO 🔥 Extension function

fun String.upperCaseAndTrim(): String {
    // INFO The this keyword inside an extension function corresponds to the receiver object
    return this.toUpperCase().trim()
}

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list this[index1] = this[index2]
    this[index2] = tmp
}


// INFO 🔥 Extensions are resolved statically
open class C

class D : C()

fun C.foo() = "c"
fun D.foo() = "d"

fun printFoo(c: C) {
    println(c.foo())
}

// INFO 🔥 calls member function instead of extension
class Cex {
    fun foo() {
        println("member")
    }
}

/*
 If a class has a member function, and an extension function is de􏰁fined
 which has the same receiver type, the same name is applicable to given arguments,
 the member always wins. For example:
 🔥 invoking foo() calls MEMBER not EXTENSION function
 */
fun Cex.foo() {
    println("extension")
}

// INFO 🔥 Overloaded extension function
class CExtension {
    fun foo() {
        println("member")
    }
}

fun CExtension.foo(i: Int) {
    println("extension")
}

/*
    Note that extensions can be de􏰁ned with a nullable receiver type.
    Such extensions can be called on an object variable even if its value is null,
    and can check for this == null inside the body.
    This is what allows you to call toString() in Kotlin without checking for null:
    the check happens inside the extension function.
 */
fun Any?.toString(): String {
    if (this == null) return "null"
// after the null check, 'this' is autocast to a non-null type, so the toString()
    //  below resolves to the member function of the Any class
    return toString()
}

// INFO 🔥 Extension Properties
val <T> List<T>.lastIndex: Int get() = size - 1

// INFO 🔥 Companion Object Extensions
class MyClass {
    companion object {} // will be called "Companion"
}

fun MyClass.Companion.foo() {
    println("MyClass.Companion.foo()")
}


// INFO 🔥 Declaring Extensions as Members
class G {
    fun bar() {
        println("G bar()")
    }
}

class F {
    fun baz() {
        println("F baz()")
    }

    fun G.foo() {
        bar() // calls G.bar
        baz() // calls F.baz
    }

    fun caller(g: G) {
        g.foo()
    }
}


