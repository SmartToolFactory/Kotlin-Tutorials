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


open class A {

    // INFO This method needs to be overridden in classes that extend this class
    open fun f() {
        print("A")
    }

    fun a() {
        print("a")
    }
}

interface B {

    // interface members are 'open' by default
    fun f() {
        print("B")
    }

    fun b() {
        print("b")
    }

    // INFO f(), and b() methods are not ABSTRACT but foo is abstract and must be
    // overridden on class that implement B interface
    fun foo()
}

class C() : A(), B {

    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // INFO only invokes methods called with super
    // 🔥 The compiler requires f() to be overridden because it's open fun in A
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f() }
    }

}

class InterfaceTestClass() : B {

    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

