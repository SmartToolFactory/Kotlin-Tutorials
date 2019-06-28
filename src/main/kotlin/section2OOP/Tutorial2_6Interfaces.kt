package section2OOP

fun main(args: Array<String>) {

    val child = Child()
    println("Child propertyWithImplementation ${child.propertyWithImplementation}")
    child.foo()
    child.prop = 2
    child.foo()


}


interface MyInterface {
    fun bar()
    fun foo() {
        // optional body
        println("MyInterface foo()")
    }
}

interface MyInterface2 {

    val prop: Int // abstract
    // INFO 🔥⚠️ This CANNOT be var -> Property in interface cannot have a backing field
    val propertyWithImplementation: String get() = "foo"

    fun foo() {
        println(prop)
    }

}

class Child : MyInterface2 {
    override var prop: Int = 29
}



