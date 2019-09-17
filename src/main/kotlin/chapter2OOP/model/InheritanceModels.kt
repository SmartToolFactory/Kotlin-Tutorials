package chapter2OOP.model


open class Base(val name: String) {

    init {
        // INFO called 2nd
        println("Initializing Base")
    }

    open val size: Int =
        // INFO called 3rd
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
    name: String,
    val lastName: String
)
// INFO called 1st
    : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        // INFO called 4th
        println("Initializing Derived")
    }

    override val size: Int =
        // INFO called 5th
        (super.size + lastName.length).also { println("Initializing size in Derived:$it") }
}


open class BaseClassA {

    // INFO This method needs to be overridden in classes that extend this class
    open fun f() {
        print("BaseClassA")
    }

    fun a() {
        print("a")
    }
}

interface InterfaceB {

    // interface members are 'open' by default
    fun f() {
        print("InterfaceB")
    }

    fun b() {
        print("b")
    }

    // INFO f(), and b() methods are not ABSTRACT but foo is abstract and must be
    // overridden on class that implement InterfaceB interface
    fun foo()
}

class C() : BaseClassA(), InterfaceB {

    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // INFO only invokes methods called with super
    // 🔥 The compiler requires f() to be overridden because it's open fun in BaseClassA
    override fun f() {
        super<BaseClassA>.f() // call to BaseClassA.f()
        super<InterfaceB>.f() // call to InterfaceB.f() }
    }

}

class InterfaceTestClass() : InterfaceB {

    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

