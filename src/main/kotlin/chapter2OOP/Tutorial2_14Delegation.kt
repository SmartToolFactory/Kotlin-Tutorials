package chapter2OOP

fun main() {

    // INFO 🔥 Implementation by Delegation
    val b = BaseImpl(10)
    Derived(b).print()

    // INFO 🔥 Overriding a member of an interface implemented by delegation
    val b2 = BaseImpl2(10)
    Derived2(b2).printMessage() // prints abc
    Derived2(b2).printMessageLine() // prints 10

    // INFO 🔥 Members overridden in derived class
    val b3 = BaseImpl3(10)
    val derived = Derived3(b3)
    derived.print() // prints BaseImpl: x = 10
    println("Derived message: ${derived.message}") // prints Message of Derived

}

// INFO 🔥 Implementation by Delegation
interface Base {
    fun print()
}


class BaseImpl(val x: Int) : Base {
    override fun print() {
        println(x)
    }
}

/**
 * The by-clause in the supertype list for Derived indicates that b will be stored internally in objects
 * of Derived and the compiler will generate all the methods of Base that forward to b .
 */
class Derived(b: Base) : Base by b

// INFO 🔥 Overriding a member of an interface implemented by delegation

interface Base2 {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl2(val x: Int) : Base2 {
    override fun printMessage() {
        println(x)
    }

    override fun printMessageLine() {
        println(x)
    }
}

//Overrides work as you might expect: the compiler will use your override implementations
// instead of those in the delegate object.
class Derived2(b: Base2) : Base2 by b {
    override fun printMessage() {
        println("abc")
    }
}

// INFO 🔥 Members overridden in derived class
interface Base3 {
    val message: String
    fun print()
}

class BaseImpl3(val x: Int) : Base3 {
    override val message = "BaseImpl: x = $x"
    override fun print() {
        println(message)
    }
}

class Derived3(b: Base3) : Base3 by b {
    // 🔥 ⚠️ This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

