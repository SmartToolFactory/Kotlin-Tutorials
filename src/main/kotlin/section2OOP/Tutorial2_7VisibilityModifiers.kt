package section2OOP

fun main(args: Array<String>) {
    val subclass = Subclass()
    // INFO ⚠️ only c, and d properties are visible to instance of derived class
    println("Subclass c ${subclass.c}, d ${subclass.d}")

}

open class Outer {

    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4 // public by default

    protected class Nested {
        public val e: Int = 5
    }

}

class Subclass : Outer() {

    // a is not visible
// b, c and d are visible // Nested and e are visible
    override val b = 5 // 'b' is protected }

    fun test() {
        println("b $b, c $c, d $d")
        val nested = Nested()

    }
}

class Unrelated(o: Outer) {
// o.a, o.b are not visible
// o.c and o.d are visible (same module)
// Outer.Nested is not visible, and Nested::e is not visible either

    init {
        // INFO ⚠️ not Visible
//        val nestedClass = Nested()
    }
}